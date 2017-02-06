cmake_minimum_required(VERSION 3.4.1)

aux_source_directory(. Q_SRC)

add_library(queue SHARED ${Q_SRC})

target_link_libraries(queue)