public class FontSet {
    private final int FONTSET_SIZE = 80;

    private uInt8[] fontset =
    {
        new uInt8((short)0xF0), new uInt8((short)0x90), new uInt8((short)0x90), new uInt8((short)0x90), new uInt8((short)0xF0), // 0
            new uInt8((short)0x20), new uInt8((short)0x60), new uInt8((short)0x20), new uInt8((short)0x20), new uInt8((short)0x70), // 1
            new uInt8((short)0xF0), new uInt8((short)0x10), new uInt8((short)0xF0), new uInt8((short)0x80), new uInt8((short)0xF0), // 2
            new uInt8((short)0xF0), new uInt8((short)0x10), new uInt8((short)0xF0), new uInt8((short)0x10), new uInt8((short)0xF0), // 3
            new uInt8((short)0x90), new uInt8((short)0x90), new uInt8((short)0xF0), new uInt8((short)0x10), new uInt8((short)0x10), // 4
            new uInt8((short)0xF0), new uInt8((short)0x80), new uInt8((short)0xF0), new uInt8((short)0x10), new uInt8((short)0xF0), // 5
            new uInt8((short)0xF0), new uInt8((short)0x80), new uInt8((short)0xF0), new uInt8((short)0x90), new uInt8((short)0xF0), // 6
            new uInt8((short)0xF0), new uInt8((short)0x10), new uInt8((short)0x20), new uInt8((short)0x40), new uInt8((short)0x40), // 7
            new uInt8((short)0xF0), new uInt8((short)0x90), new uInt8((short)0xF0), new uInt8((short)0x90), new uInt8((short)0xF0), // 8
            new uInt8((short)0xF0), new uInt8((short)0x90), new uInt8((short)0xF0), new uInt8((short)0x10), new uInt8((short)0xF0), // 9
            new uInt8((short)0xF0), new uInt8((short)0x90), new uInt8((short)0xF0), new uInt8((short)0x90), new uInt8((short)0x90), // A
            new uInt8((short)0xE0), new uInt8((short)0x90), new uInt8((short)0xE0), new uInt8((short)0x90), new uInt8((short)0xE0), // B
            new uInt8((short)0xF0), new uInt8((short)0x80), new uInt8((short)0x80), new uInt8((short)0x80), new uInt8((short)0xF0), // C
            new uInt8((short)0xE0), new uInt8((short)0x90), new uInt8((short)0x90), new uInt8((short)0x90), new uInt8((short)0xE0), // D
            new uInt8((short)0xF0), new uInt8((short)0x80), new uInt8((short)0xF0), new uInt8((short)0x80), new uInt8((short)0xF0), // E
            new uInt8((short)0xF0), new uInt8((short)0x80), new uInt8((short)0xF0), new uInt8((short)0x80), new uInt8((short)0x80)  // F
    };

    public FontSet() {
    }

    public uInt8[] getFontset() {
        return fontset;
    }

    public int getFONTSET_SIZE() {
        return FONTSET_SIZE;
    }
}
