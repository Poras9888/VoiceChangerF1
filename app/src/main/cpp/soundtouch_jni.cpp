#include <jni.h>
#include <cmath>
#include <cstdint>
#include <vector>

#ifdef USE_SOUNDTOUCH
#include <SoundTouch.h>
using soundtouch::SAMPLETYPE;
#endif

namespace {
static inline short clamp_to_short(float x) {
    if (x > 32767.0f) return 32767;
    if (x < -32768.0f) return -32768;
    return static_cast<short>(x);
}

static std::vector<float> resample_linear(const std::vector<float>& input, float step) {
    if (input.empty() || step <= 0.0f) {
        return input;
    }

    const size_t out_len = static_cast<size_t>(std::max(1.0f, static_cast<float>(input.size()) / step));
    std::vector<float> output(out_len);

    for (size_t i = 0; i < out_len; ++i) {
        float src = static_cast<float>(i) * step;
        size_t idx = static_cast<size_t>(src);
        float frac = src - static_cast<float>(idx);
        if (idx + 1 >= input.size()) {
            output[i] = input.back();
        } else {
            float a = input[idx];
            float b = input[idx + 1];
            output[i] = a + (b - a) * frac;
        }
    }
    return output;
}

static std::vector<short> process_fallback(const std::vector<short>& in, float pitchSemiTones, float tempo) {
    if (in.empty()) {
        return in;
    }

    if (tempo <= 0.0f) {
        tempo = 1.0f;
    }

    const float pitch_ratio = std::pow(2.0f, pitchSemiTones / 12.0f);

    std::vector<float> work(in.size());
    for (size_t i = 0; i < in.size(); ++i) {
        work[i] = static_cast<float>(in[i]);
    }

    if (std::fabs(pitch_ratio - 1.0f) > 0.001f) {
        work = resample_linear(work, pitch_ratio);
    }

    if (std::fabs(tempo - 1.0f) > 0.001f) {
        work = resample_linear(work, tempo);
    }

    std::vector<short> out(work.size());
    for (size_t i = 0; i < work.size(); ++i) {
        out[i] = clamp_to_short(work[i]);
    }
    return out;
}
}  // namespace

extern "C"
JNIEXPORT jshortArray JNICALL
Java_com_voice_changer_sound_effects_recorder_audio_SoundTouchBridge_nativeProcess(
        JNIEnv* env,
        jclass,
        jshortArray pcm,
        jfloat pitchSemiTones,
        jfloat tempo) {
    jsize len = env->GetArrayLength(pcm);
    if (len <= 0) {
        return env->NewShortArray(0);
    }

    std::vector<jshort> in(static_cast<size_t>(len));
    env->GetShortArrayRegion(pcm, 0, len, in.data());

    std::vector<short> processed;

#ifdef USE_SOUNDTOUCH
    soundtouch::SoundTouch st;
    st.setSampleRate(44100);
    st.setChannels(1);
    st.setPitchSemiTones(pitchSemiTones);
    st.setTempo(tempo <= 0.0f ? 1.0f : tempo);

    std::vector<SAMPLETYPE> input_float(static_cast<size_t>(len));
    for (jsize i = 0; i < len; ++i) {
        input_float[static_cast<size_t>(i)] = static_cast<float>(in[static_cast<size_t>(i)]) / 32768.0f;
    }

    st.putSamples(input_float.data(), static_cast<uint32_t>(input_float.size()));
    st.flush();

    std::vector<SAMPLETYPE> chunk(4096);
    std::vector<SAMPLETYPE> out_float;
    while (true) {
        uint32_t received = st.receiveSamples(chunk.data(), static_cast<uint32_t>(chunk.size()));
        if (received == 0) {
            break;
        }
        out_float.insert(out_float.end(), chunk.begin(), chunk.begin() + static_cast<long>(received));
    }

    if (out_float.empty()) {
        processed.assign(in.begin(), in.end());
    } else {
        processed.resize(out_float.size());
        for (size_t i = 0; i < out_float.size(); ++i) {
            processed[static_cast<size_t>(i)] = clamp_to_short(out_float[static_cast<size_t>(i)] * 32767.0f);
        }
    }
#else
    processed = process_fallback(std::vector<short>(in.begin(), in.end()), pitchSemiTones, tempo);
#endif

    jshortArray out = env->NewShortArray(static_cast<jsize>(processed.size()));
    if (out == nullptr) {
        return nullptr;
    }

    env->SetShortArrayRegion(out, 0, static_cast<jsize>(processed.size()),
                             reinterpret_cast<const jshort*>(processed.data()));
    return out;
}
