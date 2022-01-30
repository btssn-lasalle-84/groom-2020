#include "bdd.h"
#include "ui_bdd.h"

Bdd::Bdd(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::Bdd)
{
    ui->setupUi(this);
}

Bdd::~Bdd()
{
    delete ui;
}
