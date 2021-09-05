public class uInt8 {
    private short uInt8Value;

    public uInt8(){

    }

    public uInt8(short s){
        uInt8Value = (short) (s & 0x00ff);
    }

    public short getuInt8Value(){
        return uInt8Value;
    }
}
