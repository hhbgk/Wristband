cmake_minimum_required(VERSION 3.4.1)

add_subdirectory(queue)

aux_source_directory(. JNI_SRC)

add_library(bt_band SHARED ${JNI_SRC})

target_link_libraries(bt_band queue log android)