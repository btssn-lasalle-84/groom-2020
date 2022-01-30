#ifndef MESSAGEPERSONNALISE_H
#define MESSAGEPERSONNALISE_H

#include <QtWidgets>

/**
 * @brief
 * Classe permetant de personnalisé son message
 * @class MessagePersonnalise messagepersonnalise.h "messagepersonnalise.h"
 */

namespace Ui {
class MessagePersonnalise;
}

class MessagePersonnalise : public QDialog
{
    Q_OBJECT

public:
    /**
     * @brief
     * Constructeur de la classe MessagePersonnalise
     * @fn MessagePersonnalise
     * @param parent
     */
    explicit MessagePersonnalise(QWidget *parent = nullptr);
    /**
     * @brief
     * Destructeur de la classe MessagePersonnalise
     * @fn ~MessagePersonnalise
     * @param parent
     */
    ~MessagePersonnalise();

private:
    Ui::MessagePersonnalise *ui;            /** L'interface utilisateur */
};

#endif // MESSAGEPERSONNALISE_H
