#-------------------------------------------------
#
# Project created by QtCreator 2020-03-04T13:49:58
#
#-------------------------------------------------

QT       += core gui serialport network bluetooth charts positioning

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = GroomQT
TEMPLATE = app

# The following define makes your compiler emit warnings if you use
# any feature of Qt which has been marked as deprecated (the exact warnings
# depend on your compiler). Please consult the documentation of the
# deprecated API in order to know how to port your code away from it.
DEFINES += QT_DEPRECATED_WARNINGS

# You can also make your code fail to compile if you use deprecated APIs.
# In order to do so, uncomment the following line.
# You can also select to disable deprecated APIs only up to a certain version of Qt.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

CONFIG += c++11

SOURCES += \
    bdd.cpp \
        main.cpp \
        ihmgroom.cpp \
        importation.cpp \
        messagepersonnalise.cpp \
    communicationbluetooth.cpp \
    controle.cpp

HEADERS += \
    bdd.h \
        ihmgroom.h \
        importation.h \
        messagepersonnalise.h \
    communicationbluetooth.h \
    controle.h

FORMS += \
    bdd.ui \
        ihmgroom.ui \
        importation.ui \
        messagepersonnalise.ui \
    communicationbluetooth.ui

RESOURCES += \
        icon.qrc
