package packWork;

public class Algorithm extends BaseClass {

    private int a[], r[], g[], b[];
    static int counter;

    {
        a = null;
        r = null;
        g = null;
        b = null;
        System.out.println("Algorithm nonstatic block\n");

    }

    static {
        counter = 0;
        System.out.println("Algorithm static block\n");
    }

    public Algorithm() {
        this.a = null;
        this.r = null;
        this.g = null;
        this.b = null;
        System.out.println("Constructor no params");
    }

    public Algorithm(int length) {
        this.a = new int[length];
        this.r = new int[length];
        this.g = new int[length];
        this.b = new int[length];
        System.out.println("Constructor with length param");
    }

    public Algorithm(int a, int r, int g, int b, int i) {
        this.a[i] = a;
        this.r[i] = r;
        this.g[i] = g;
        this.b[i] = b;
    }

    public Algorithm(int a[], int r[], int g[], int b[]) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int[] executeAlgorithm(int length) {
        int[] pixels = new int[length];
        for (int i = 0; i < length; i++) {

            int avg = (int) (r[i] * 0.2126 + g[i] * 0.7152 + b[i] * 0.0722);

            pixels[i] = (a[i] << 24) | (avg << 16) | (avg << 8) | avg;
        }
        return pixels;
    }

    // public void seta(int a, int i) {
    // this.a[i] = a;
    // }

    // public void setr(int r, int i) {
    // this.r[i] = r;
    // }

    // public void setg(int g, int i) {
    // this.g[i] = g;
    // }

    // public void setb(int b, int i) {
    // this.b[i] = b;
    // }

    public void seta(int... values) {
        this.a[values[1]] = values[0];
    }

    public void setr(int... values) {
        this.r[values[1]] = values[0];
    }

    public void setg(int... values) {
        this.g[values[1]] = values[0];
    }

    public void setb(int... values) {
        this.b[values[1]] = values[0];
    }

    public int[] geta() {
        return this.a;
    }

    public int[] getr() {
        return this.r;
    }

    public int[] getg() {
        return this.g;
    }

    public int[] getb() {
        return this.b;
    }

    public void showFromInterface() {
        System.out.println("Output from Algorithm implementing mehtod from interface");
    }

    public void showFromAbstract() {
        System.out.println("Output from Algorithm implementing abstract method");
    }
}
