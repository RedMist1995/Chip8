public class uInt32 {
    private long uInt32Value;

    public uInt32(){

    }

    public uInt32(long i){
        uInt32Value = (long) i & 0x00000000ffffffff;
    }

    public long getuInt32Value(){
        return uInt32Value;
    }
}
