public class uInt16 {
    private int uInt16Value;

    public uInt16(){

    }

    public uInt16(int i){
        uInt16Value = (i & 0x0000ffff);
    }

    public int getuInt16Value(){
        return uInt16Value;
    }
}
