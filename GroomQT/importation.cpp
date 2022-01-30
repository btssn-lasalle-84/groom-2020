#include "importation.h"
#include "ui_importation.h"

Importation::Importation(QWidget *parent) : QDialog(parent), ui(new Ui::Importation)
{
     qDebug() << Q_FUNC_INFO;
    ui->setupUi(this);
}

Importation::~Importation()
{
    delete ui;
    qDebug() << Q_FUNC_INFO;
}
