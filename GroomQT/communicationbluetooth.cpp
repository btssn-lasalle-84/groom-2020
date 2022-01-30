#include "communicationbluetooth.h"
#include "ui_communicationbluetooth.h"

CommunicationBluetooth::CommunicationBluetooth(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::CommunicationBluetooth)
{
     qDebug() << Q_FUNC_INFO;
    ui->setupUi(this);
}

CommunicationBluetooth::~CommunicationBluetooth()
{
    delete ui;
    qDebug() << Q_FUNC_INFO;
}
