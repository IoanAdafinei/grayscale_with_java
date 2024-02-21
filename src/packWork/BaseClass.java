package packWork;

public abstract class BaseClass implements BaseClassInterface {
    @Override
    public void showFromInterface() {
        System.out.println("Output from BaseClass implementing mehtod from interface");
    }

    public abstract void showFromAbstract();
}
