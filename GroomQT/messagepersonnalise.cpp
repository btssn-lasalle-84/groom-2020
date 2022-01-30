#include "messagepersonnalise.h"
#include "ui_messagepersonnalise.h"

MessagePersonnalise::MessagePersonnalise(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::MessagePersonnalise)
{
     qDebug() << Q_FUNC_INFO;
    ui->setupUi(this);
}

MessagePersonnalise::~MessagePersonnalise()
{
    delete ui;
    qDebug() << Q_FUNC_INFO;
}
