import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Chip8 {
    private static final Chip8Components chip8 = new Chip8Components();
    private static final Chip8OPCodes chip8OPCodes = new Chip8OPCodes(chip8);
    private final static int VIDEO_WIDTH = 64;
    private final static int VIDEO_HEIGHT = 32;

    public static void main(String[] args){
        if(args.length < 1){
            System.out.println("No File Name given");
            System.exit(0);
        }

        String fileName = args[0];
        int videoScale = Integer.parseInt(args[1]);
        int cycleDelay = Integer.parseInt(args[2]);

        loadRom(fileName);

        JFrame jFrame = new JFrame();
        jFrame.setSize(VIDEO_WIDTH*videoScale + 30,VIDEO_HEIGHT*videoScale + 40);
        jFrame.setLocation(200,200);
        jFrame.setVisible(true);

        Platform platform = new Platform("Chip 8 Emulator", VIDEO_WIDTH, VIDEO_HEIGHT, videoScale, chip8);
        platform.setFocusable(true);
        jFrame.setContentPane(platform);

        jFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                jFrame.dispose();
            }
        });

        platform.drawPixel();

        LocalTime lastCycleTime = LocalTime.now();

        platform.requestFocusInWindow();
        while(jFrame.isVisible()){
            LocalTime currentTime = LocalTime.now();
            long delayTime = ChronoUnit.MILLIS.between(lastCycleTime, currentTime);
            if(delayTime > cycleDelay){
                lastCycleTime = currentTime;
                cycle();
                platform.drawPixel();
            }
        }
    }

    public static void loadRom(String fileName){
        File file = new File(fileName);
        long size = file.length();
        final uInt16 startAddress = new uInt16(Short.decode("0x200"));
        final uInt8 fontStartAddress = new uInt8(Byte.decode("0x50"));

        try{
            byte[] buffer = Files.readAllBytes(file.toPath());
            uInt8[] memory = new uInt8[4096];
            for(short i = 0; i< size; i++){
                memory[(startAddress.getuInt16Value()+i)] = new uInt8((byte)(buffer[i]&0xFF));
            }
            FontSet fontSet = new FontSet();

            for(short i = 0; i<fontSet.getFONTSET_SIZE(); i++){
                memory[(fontStartAddress.getuInt8Value()+i)] = fontSet.getFontset()[i];
            }
            chip8.setMemory(memory);
            chip8.setPc(startAddress);

        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static void cycle(){
        chip8.setOpcode(new uInt16((short) (chip8.getMemory()[chip8.getPc().getuInt16Value()].getuInt8Value() << 8 | chip8.getMemory()[(chip8.getPc().getuInt16Value() + 1)].getuInt8Value())));
        chip8.setPc(new uInt16((short) (chip8.getPc().getuInt16Value() + 2)));

        decodeOpCode();

        if(chip8.getDelayTimer().getuInt8Value() > 0){
            chip8.setDelayTimer(new uInt8((byte) (chip8.getDelayTimer().getuInt8Value() - 1)));
        }

        if(chip8.getSoundTimer().getuInt8Value() > 0){
            chip8.setSoundTimer(new uInt8((byte) (chip8.getSoundTimer().getuInt8Value() - 1)));
        }
    }

    public static void decodeOpCode(){
        uInt16 opCode = chip8.getOpcode();
        int firstOPValue = (opCode.getuInt16Value() & 0xF000) >> 12;
        int lastOPValue = opCode.getuInt16Value() & 0x000F;
        switch (firstOPValue){
            case 0x0:
                switch (lastOPValue){
                    case 0x0:
                        chip8OPCodes.OP_OOEO();
                        break;
                    case 0xE:
                        chip8OPCodes.OP_OOEE();
                        break;
                    case 0xE + 1:
                    default:
                        break;
                }
                break;
            case 0x1:
                chip8OPCodes.OP_1nnn();
                break;
            case 0x2:
                chip8OPCodes.OP_2nnn();
                break;
            case 0x3:
                chip8OPCodes.OP_3xkk();
                break;
            case 0x4:
                chip8OPCodes.OP_4xkk();
                break;
            case 0x5:
                chip8OPCodes.OP_5xy0();
                break;
            case 0x6:
                chip8OPCodes.OP_6xkk();
                break;
            case 0x7:
                chip8OPCodes.OP_7xkk();
                break;
            case 0x8:
                switch (lastOPValue) {
                    case 0x0:
                        chip8OPCodes.OP_8xy0();
                        break;
                    case 0x1:
                        chip8OPCodes.OP_8xy1();
                        break;
                    case 0x2:
                        chip8OPCodes.OP_8xy2();
                        break;
                    case 0x3:
                        chip8OPCodes.OP_8xy3();
                        break;
                    case 0x4:
                        chip8OPCodes.OP_8xy4();
                        break;
                    case 0x5:
                        chip8OPCodes.OP_8xy5();
                        break;
                    case 0x6:
                        chip8OPCodes.OP_8xy6();
                        break;
                    case 0x7:
                        chip8OPCodes.OP_8xy7();
                        break;
                    case 0xE:
                        chip8OPCodes.OP_8xyE();
                        break;
                    case 0xE+1:
                    default:
                        break;
                }
                break;
            case 0x9:
                chip8OPCodes.OP_9xy0();
                break;
            case 0xA:
                chip8OPCodes.OP_Annn();
                break;
            case 0xB:
                chip8OPCodes.OP_Bnnn();
                break;
            case 0xC:
                chip8OPCodes.OP_Cxkk();
                break;
            case 0xD:
                chip8OPCodes.OP_Dxyn();
                break;
            case 0xE:
                switch (lastOPValue) {
                    case 0x1:
                        chip8OPCodes.OP_ExA1();
                        break;
                    case 0xE:
                        chip8OPCodes.OP_Ex9E();
                        break;
                    case 0xE+1:
                    default:
                        break;
                }
                break;
            case 0xF:
                int lastOPValues = opCode.getuInt16Value() & 0x00FF;
                switch (lastOPValues) {
                    case 0x07:
                        chip8OPCodes.OP_Fx07();
                        break;
                    case 0x0A:
                        chip8OPCodes.OP_Fx0A();
                        break;
                    case 0x15:
                        chip8OPCodes.OP_Fx15();
                        break;
                    case 0x18:
                        chip8OPCodes.OP_Fx18();
                        break;
                    case 0x1E:
                        chip8OPCodes.OP_Fx1E();
                        break;
                    case 0x29:
                        chip8OPCodes.OP_Fx29();
                        break;
                    case 0x33:
                        chip8OPCodes.OP_Fx33();
                        break;
                    case 0x55:
                        chip8OPCodes.OP_Fx55();
                        break;
                    case 0x65:
                        chip8OPCodes.OP_Fx65();
                        break;
                    case 0x65+1:
                    default:
                        break;
                }
                break;
            case 0xF + 1:
            default:
                break;
        }
    }
}
