#ifndef COMMUNICATIONBLUETOOTH_H
#define COMMUNICATIONBLUETOOTH_H

#include <QtWidgets>


/**
 * @brief
 * Class permettant de mettre en place une communication bluetooth
 * @class CommunicationBluetooth communicationbluetooth.h "communicationbluetooth.h"
 */
class IhmGroom;
namespace Ui {
class CommunicationBluetooth;
}

class CommunicationBluetooth : public QDialog
{
    Q_OBJECT

public:
    /**
     * @brief
     * Constructeur classe CommunicationBluetooth
     * @fn CommunicationBluetooth
     * @param parent
     */
    explicit CommunicationBluetooth(QWidget *parent = nullptr);
    /**
     * @brief
     * Destructeur classe CommunicationBluetooth
     * @fn ~CommunicationBluetooth
     * @param parent
     */
    ~CommunicationBluetooth();

private:
    Ui::CommunicationBluetooth *ui; /** L'interface utilisateur */
};

#endif // COMMUNICATIONBLUETOOTH_H
