cmake_minimum_required(VERSION 3.22.1)
project(soundtouch_jni)

set(SOUNDTOUCH_DIR ${CMAKE_CURRENT_SOURCE_DIR}/third_party/soundtouch)
if (NOT EXISTS ${SOUNDTOUCH_DIR}/CMakeLists.txt)
    message(FATAL_ERROR "Vendored SoundTouch not found at ${SOUNDTOUCH_DIR}")
endif()

set(SOUNDSTRETCH OFF CACHE BOOL "Disable soundstretch binary" FORCE)
set(SOUNDTOUCH_DLL OFF CACHE BOOL "Disable SoundTouch DLL wrapper" FORCE)
set(OPENMP OFF CACHE BOOL "Disable OpenMP for Android portability" FORCE)
set(INTEGER_SAMPLES OFF CACHE BOOL "Use float sample processing" FORCE)

add_subdirectory(${SOUNDTOUCH_DIR} ${CMAKE_CURRENT_BINARY_DIR}/soundtouch-build)

add_library(soundtouch_jni SHARED soundtouch_jni.cpp)
target_compile_definitions(soundtouch_jni PRIVATE USE_SOUNDTOUCH)

find_library(log-lib log)
target_link_libraries(soundtouch_jni SoundTouch ${log-lib})
