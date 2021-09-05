import java.util.Arrays;

public class Chip8Components {

    private uInt8[] registers = new uInt8[16];
    private uInt8[] memory = new uInt8[4096];
    private uInt16 index = new uInt16(0);
    private uInt16 pc = new uInt16( 0);
    private uInt16[] stack = new uInt16[16];
    private uInt8 sp = new uInt8((short) 0);
    private uInt8 delayTimer = new uInt8((short) 0);
    private uInt8 soundTimer = new uInt8((short) 0);
    private uInt8[] keypad = new uInt8[16];
    private uInt32[] video = new uInt32[64 * 32];
    private uInt16 opcode = new uInt16(0);

    public Chip8Components() {
        uInt32 longZero = new uInt32(0);
        uInt16 intZero = new uInt16(0);
        uInt8 shortZero = new uInt8((short) 0);
        Arrays.fill(registers, shortZero);
        Arrays.fill(memory, shortZero);
        Arrays.fill(stack, intZero);
        Arrays.fill(keypad, shortZero);
        Arrays.fill(video, longZero);
    }

    public uInt8[] getRegisters() {
        return registers;
    }

    public void setRegisters(uInt8[] registers) {
        this.registers = registers;
    }

    public uInt8[] getMemory() {
        return memory;
    }

    public void setMemory(uInt8[] memory) {
        this.memory = memory;
    }

    public uInt16 getIndex() {
        return index;
    }

    public void setIndex(uInt16 index) {
        this.index = index;
    }

    public uInt16 getPc() {
        return pc;
    }

    public void setPc(uInt16 pc) {
        this.pc = pc;
    }

    public uInt16[] getStack() {
        return stack;
    }

    public void setStack(uInt16[] stack) {
        this.stack = stack;
    }

    public uInt8 getSp() {
        return sp;
    }

    public void setSp(uInt8 sp) {
        this.sp = sp;
    }

    public uInt8 getDelayTimer() {
        return delayTimer;
    }

    public void setDelayTimer(uInt8 delayTimer) {
        this.delayTimer = delayTimer;
    }

    public uInt8 getSoundTimer() {
        return soundTimer;
    }

    public void setSoundTimer(uInt8 soundTimer) {
        this.soundTimer = soundTimer;
    }

    public uInt8[] getKeypad() {
        return keypad;
    }

    public void setKeypad(uInt8[] keypad) {
        this.keypad = keypad;
    }

    public uInt32[] getVideo() {
        return video;
    }

    public void setVideo(uInt32[] video) {
        this.video = video;
    }

    public uInt16 getOpcode() {
        return opcode;
    }

    public void setOpcode(uInt16 opcode) {
        this.opcode = opcode;
    }
}
