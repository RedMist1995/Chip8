import java.util.Arrays;
import java.util.Random;

public class Chip8OPCodes {
    private final Chip8Components chip8;
    private int stackPointer = 0;
    final uInt16 fontStartAddress = new uInt16(Short.decode("0x50"));

    public Chip8OPCodes(Chip8Components chip8Components) {
        this.chip8 = chip8Components;
    }

    public uInt8 getRandomNumber(){
        Random rand = new Random();
        return new uInt8((short) rand.nextInt(Short.MAX_VALUE));
    }

    //CLS - Clear Screen
    public void OP_OOEO(){
        uInt32 zero = new uInt32(0);
        Arrays.fill(chip8.getVideo(), zero);
    }

    //RET - Return from Subroutine
    public void OP_OOEE(){
        --stackPointer;
        chip8.setPc(chip8.getStack()[stackPointer]);
    }

    //1nnn - JP Addr
    public void OP_1nnn(){
        uInt16 address = new uInt16((chip8.getOpcode().getuInt16Value() & 0x0FFF));
        chip8.setPc(address);
    }

    //2nnn - CALL Addr
    public void OP_2nnn(){
        uInt16 address = new uInt16((chip8.getOpcode().getuInt16Value() & 0x0FFF));

        chip8.getStack()[stackPointer] = chip8.getPc();
        ++stackPointer;
        chip8.setPc(address);
    }

    //3xkk - Skip Next Instruction if Vx = kk
    public void OP_3xkk(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 short_ = new uInt8((short) (chip8.getOpcode().getuInt16Value() & 0x00FF));

        if(chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() == short_.getuInt8Value()){
            chip8.setPc(new uInt16((chip8.getPc().getuInt16Value()+2)));
        }
    }

    //4xkk - Skip Next Instruction if Vx != kk
    public void OP_4xkk(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 short_ = new uInt8((short) (chip8.getOpcode().getuInt16Value() & 0x00FF));

        if(chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() != short_.getuInt8Value()){
            chip8.setPc(new uInt16((chip8.getPc().getuInt16Value()+2)));
        }
    }

    //5xy0 - Skip Next Instruction if Vx = Vy
    public void OP_5xy0(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 Vy = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x00F0) >> 4));

        if(chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() == chip8.getRegisters()[Vy.getuInt8Value()].getuInt8Value()){
            chip8.setPc(new uInt16((chip8.getPc().getuInt16Value()+2)));
        }
    }

    //6xkk - Set Vx to kk
    public void OP_6xkk(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 short_ = new uInt8((short) (chip8.getOpcode().getuInt16Value() & 0x00FF));

        chip8.getRegisters()[Vx.getuInt8Value()] = short_;
    }

    //7xkk - Set Vx = Vx + kk
    public void OP_7xkk(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 short_ = new uInt8((short) (chip8.getOpcode().getuInt16Value() & 0x00FF));

        chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) (chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() + short_.getuInt8Value()));
    }

    //8xy0 - Set Vx = Vy
    public void OP_8xy0(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 Vy = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x00F0) >> 4));

        chip8.getRegisters()[Vx.getuInt8Value()] = chip8.getRegisters()[Vy.getuInt8Value()];
    }

    //8xy1 - Set Vx OR Vy
    public void OP_8xy1(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 Vy = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x00F0) >> 4));

        uInt8 orValue = new uInt8((short) (chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() | chip8.getRegisters()[Vy.getuInt8Value()].getuInt8Value()));

        chip8.getRegisters()[Vx.getuInt8Value()] = orValue;
    }

    //8xy2 - Set Vx AND Vy
    public void OP_8xy2(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 Vy = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x00F0) >> 4));

        chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) (chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() & chip8.getRegisters()[Vy.getuInt8Value()].getuInt8Value()));
    }

    //8xy3 - Set Vx XOR Vy
    public void OP_8xy3(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 Vy = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x00F0) >> 4));

        chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) (chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() ^ chip8.getRegisters()[Vy.getuInt8Value()].getuInt8Value()));
    }

    //8xy4 - Set Vx = Vx + Vy set Carry Flag
    public void OP_8xy4(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 Vy = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x00F0) >> 4));

        uInt16 sum = new uInt16(((int) chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() + (int) chip8.getRegisters()[Vy.getuInt8Value()].getuInt8Value()));
        if(sum.getuInt16Value() > 255){
            chip8.getRegisters()[15] = new uInt8((short) 1);
        } else {
            chip8.getRegisters()[15] = new uInt8((short) 0);
        }

        chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) (sum.getuInt16Value() & 0xFF));
    }

    //8xy5 - Set Vx = Vx - Vy set NOT Borrow Flag
    public void OP_8xy5(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 Vy = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x00F0) >> 4));

        if(chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() > chip8.getRegisters()[Vy.getuInt8Value()].getuInt8Value()){
            chip8.getRegisters()[15] = new uInt8((short) 1);
        } else {
            chip8.getRegisters()[15] = new uInt8((short) 0);
        }

        chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) (chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() - chip8.getRegisters()[Vy.getuInt8Value()].getuInt8Value()));
    }

    //8xy6 - Set Vx = Vx SHR 1
    public void OP_8xy6(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));

        chip8.getRegisters()[15] = new uInt8((short) (chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() & 0x1));

        chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) (chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() >> 1));
    }

    //8xy7 - Set Vx = Vy - Vx set NOT Borrow Flag
    public void OP_8xy7(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 Vy = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x00F0) >> 4));

        if(chip8.getRegisters()[Vy.getuInt8Value()].getuInt8Value() > chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value()){
            chip8.getRegisters()[15] = new uInt8((short) 1);
        } else {
            chip8.getRegisters()[15] = new uInt8((short) 0);
        }

        chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) (chip8.getRegisters()[Vy.getuInt8Value()].getuInt8Value() - chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value()));
    }

    //8xy6 - Set Vx = Vx SHL 1
    public void OP_8xyE(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));

        chip8.getRegisters()[15] = new uInt8((short) ((chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() & 0x80) >> 7));

        chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) (chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() << 1));
    }

    //9xy0 - Skip Next Instruction if Vx != Vy
    public void OP_9xy0(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 Vy = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x00F0) >> 4));

        if(chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() != chip8.getRegisters()[Vy.getuInt8Value()].getuInt8Value()){
            chip8.setPc(new uInt16((chip8.getPc().getuInt16Value()+2)));
        }
    }

    //Annn - Set Index to nnn
    public void OP_Annn(){
        uInt16 address = new uInt16((chip8.getOpcode().getuInt16Value() & 0x0FFF));
        chip8.setIndex(address);
    }

    //Bnnn - Jump to Location nnn + Register[0]
    public void OP_Bnnn(){
        uInt16 address = new uInt16((chip8.getOpcode().getuInt16Value() & 0x0FFF));
        chip8.setPc(new uInt16((chip8.getRegisters()[0].getuInt8Value() + address.getuInt16Value())));
    }

    //Cxkk - Set Vx = Random short AND kk
    public void OP_Cxkk(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 short_ = new uInt8((short) (chip8.getOpcode().getuInt16Value() & 0x00FF));

        chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) (getRandomNumber().getuInt8Value() & short_.getuInt8Value()));
    }

    //Dxyn - DRW Vx, Vy, nibble
    public void OP_Dxyn(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 Vy = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x00F0) >> 4));
        uInt8 height = new uInt8((short) (chip8.getOpcode().getuInt16Value() & 0x000F));

        int VIDEO_WIDTH = 64;
        uInt8 xpos = new uInt8((short) (chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value() % VIDEO_WIDTH));
        int VIDEO_HEIGHT = 32;
        uInt8 ypos = new uInt8((short) (chip8.getRegisters()[Vy.getuInt8Value()].getuInt8Value() % VIDEO_HEIGHT));

        chip8.getRegisters()[15] = new uInt8((short) 0);

        for(int row = 0; row < height.getuInt8Value(); row++){
            uInt8 spriteshort = chip8.getMemory()[chip8.getIndex().getuInt16Value() + row];

            for(int col = 0; col < 8; col++){
                uInt8 spritePixel = new uInt8((short) (spriteshort.getuInt8Value() & (0x80 >> col)));
                int videoAddress = (ypos.getuInt8Value() + row) * VIDEO_WIDTH + (xpos.getuInt8Value() + col);
                if(videoAddress < 2048) {
                    uInt32 screenPixel = chip8.getVideo()[videoAddress];

                    if (spritePixel.getuInt8Value() != 0) {
                        if (chip8.getVideo()[(ypos.getuInt8Value() + row) * VIDEO_WIDTH + (xpos.getuInt8Value() + col)].getuInt32Value() == 1) {
                            chip8.getRegisters()[15] = new uInt8((short) 1);
                        }
                        screenPixel = new uInt32((int) (screenPixel.getuInt32Value() ^ 1));
                        chip8.getVideo()[(ypos.getuInt8Value() + row) * VIDEO_WIDTH + (xpos.getuInt8Value() + col)] = screenPixel;
                    }
                }
            }
        }
    }

    //Ex9E - SKP Vx
    public void OP_Ex9E(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 key = chip8.getRegisters()[Vx.getuInt8Value()];

        if(chip8.getKeypad()[key.getuInt8Value()].getuInt8Value() != 0){
            chip8.setPc(new uInt16((chip8.getPc().getuInt16Value() + 2)));
        }
    }

    //ExA1 - SKNP Vx
    public void OP_ExA1(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 key = chip8.getRegisters()[Vx.getuInt8Value()];

        if(chip8.getKeypad()[key.getuInt8Value()].getuInt8Value() == 0){
            chip8.setPc(new uInt16((chip8.getPc().getuInt16Value() + 2)));
        }
    }

    //Fx07 - LD Vx, DT
    public void OP_Fx07(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        chip8.getRegisters()[Vx.getuInt8Value()] = chip8.getDelayTimer();
    }

    //Fx0A - LD Vx, K
    public void OP_Fx0A(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));

        if (chip8.getKeypad()[0].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 0);
        }
        else if (chip8.getKeypad()[1].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 1);
        }
        else if (chip8.getKeypad()[2].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 2);
        }
        else if (chip8.getKeypad()[3].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 3);
        }
        else if (chip8.getKeypad()[4].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 4);
        }
        else if (chip8.getKeypad()[5].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 5);
        }
        else if (chip8.getKeypad()[6].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 6);
        }
        else if (chip8.getKeypad()[7].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 7);
        }
        else if (chip8.getKeypad()[8].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 8);
        }
        else if (chip8.getKeypad()[9].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 9);
        }
        else if (chip8.getKeypad()[10].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 10);
        }
        else if (chip8.getKeypad()[11].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 11);
        }
        else if (chip8.getKeypad()[12].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 12);
        }
        else if (chip8.getKeypad()[13].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 13);
        }
        else if (chip8.getKeypad()[14].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 14);
        }
        else if (chip8.getKeypad()[15].getuInt8Value() != 0) {
            chip8.getRegisters()[Vx.getuInt8Value()] = new uInt8((short) 15);
        }
        else {
            chip8.setPc(new uInt16((chip8.getPc().getuInt16Value() - 2)));
        }
    }

    //Fx15 - LD DT, Vx
    public void OP_Fx15(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        chip8.setDelayTimer(chip8.getRegisters()[Vx.getuInt8Value()]);
    }

    //Fx18 - LD ST, Vx
    public void OP_Fx18(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        chip8.setSoundTimer(chip8.getRegisters()[Vx.getuInt8Value()]);
    }

    //Fx1E - ADD I, Vx
    public void OP_Fx1E(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        chip8.setIndex(new uInt16((chip8.getIndex().getuInt16Value() + chip8.getRegisters()[Vx.getuInt8Value()].getuInt8Value())));
    }

    //Fx29 - LD F, Vx
    public void OP_Fx29(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 digit = chip8.getRegisters()[Vx.getuInt8Value()];

        chip8.setIndex(new uInt16((fontStartAddress.getuInt16Value() + (5 * digit.getuInt8Value()))));
    }

    //Fx33 - LD B, Vx
    public void OP_Fx33(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));
        uInt8 value = chip8.getRegisters()[Vx.getuInt8Value()];

        chip8.getMemory()[chip8.getIndex().getuInt16Value() + 2] = new uInt8((short) (value.getuInt8Value() % 10));
        value = new uInt8((short) (value.getuInt8Value() / 10));

        chip8.getMemory()[chip8.getIndex().getuInt16Value() + 1] = new uInt8((short) (value.getuInt8Value() % 10));
        value = new uInt8((short) (value.getuInt8Value() / 10));

        chip8.getMemory()[chip8.getIndex().getuInt16Value()] = new uInt8((short) (value.getuInt8Value() % 10));
    }

    //Fx55 - LD [I], Vx
    public void OP_Fx55(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));

        for(int i = 0; i <= Vx.getuInt8Value(); ++i){
            chip8.getMemory()[chip8.getIndex().getuInt16Value() + i] = chip8.getRegisters()[i];
        }
    }

    //Fx65 - LD Vx, [I]
    public void OP_Fx65(){
        uInt8 Vx = new uInt8((short) ((chip8.getOpcode().getuInt16Value() & 0x0F00) >> 8));

        for(int i = 0; i <= Vx.getuInt8Value(); ++i){
            chip8.getRegisters()[i] = chip8.getMemory()[chip8.getIndex().getuInt16Value() + i];
        }
    }
}
