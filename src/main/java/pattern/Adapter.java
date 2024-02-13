package pattern;

public class Adapter {
    EuropeanPowerInterface europeanPowerInterface=new EuropeanPowerInterfaceImpl();
    USPowerInterface powerInterface=new SocketAdapter(europeanPowerInterface);
   // powerInterface.powerOn();
}

interface EuropeanPowerInterface{
    void switchOn();
}

class EuropeanPowerInterfaceImpl implements EuropeanPowerInterface{

    @Override
    public void switchOn() {
        System.out.println("Switched on");
    }
}
//target
interface USPowerInterface{
    void powerOn();
}
//target impl
class SocketAdapter implements USPowerInterface{

    EuropeanPowerInterface europeanPowerInterface;

    SocketAdapter(EuropeanPowerInterface europeanPowerInterface){
        this.europeanPowerInterface=europeanPowerInterface;
    }

    @Override
    public void powerOn() {
        europeanPowerInterface.switchOn();
    }
}
