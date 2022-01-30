#ifndef BDD_H
#define BDD_H

#include <QtWidgets>

namespace Ui {
class Bdd;
}

class Bdd : public QDialog
{
    Q_OBJECT

public:
    explicit Bdd(QWidget *parent = nullptr);
    ~Bdd();

private:
    Ui::Bdd *ui;
};

#endif // BDD_H
