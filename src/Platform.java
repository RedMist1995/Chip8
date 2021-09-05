import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Platform extends JPanel implements KeyListener {

    public int width;
    public int height;
    public int scale;
    public Chip8Components chip8Components;
    private BufferedImage bi;
    public Platform(String title, int width, int height, int videoScale, Chip8Components chip8Components) throws HeadlessException {
        this.width = width;
        this.height = height;
        this.scale = videoScale;
        this.chip8Components = chip8Components;
        addKeyListener(this);
        setSize(width*scale, height*scale);
        setVisible(true);
        setBackground(Color.BLACK);
    }
    public void paint(Graphics G) {
        super.paintComponents(G);
        if(bi == null) {
            initImage();
        }
        G.drawImage(bi, 0, 0, this);
    }

    private void initImage()
    {
        int w = width*scale, h = height*scale;
        bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.setPaint(Color.BLUE);
    }

    public void drawPixel() {
        for (int i = 0; i < 2048; i++) {
            if(bi == null){
                initImage();
            }
            if (chip8Components != null && chip8Components.getVideo() != null) {
                for(int j=0; j<scale; j++) {
                    for(int k = 0; k<scale; k++) {
                        Color color = Color.BLACK;;
                        if(chip8Components.getVideo()[i].getuInt32Value() != 0) {
                            color = Color.WHITE;
                        }
                        int colorValue = color.getRGB();
                        int x = ((i % 64) * scale) + j;
                        int y = (((i / 64) * scale)) + k;
                        bi.setRGB(x, y, colorValue);
                    }
                }
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
            {
                //quit = true;
            } break;

            case KeyEvent.VK_X:
            {
                chip8Components.getKeypad()[0] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_1:
            {
                chip8Components.getKeypad()[1] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_2:
            {
                chip8Components.getKeypad()[2] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_3:
            {
                chip8Components.getKeypad()[3] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_Q:
            {
                chip8Components.getKeypad()[4] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_W:
            {
                chip8Components.getKeypad()[5] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_E:
            {
                chip8Components.getKeypad()[6] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_A:
            {
                chip8Components.getKeypad()[7] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_S:
            {
                chip8Components.getKeypad()[8] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_D:
            {
                chip8Components.getKeypad()[9] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_Z:
            {
                chip8Components.getKeypad()[0xA] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_C:
            {
                chip8Components.getKeypad()[0xB] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_4:
            {
                chip8Components.getKeypad()[0xC] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_R:
            {
                chip8Components.getKeypad()[0xD] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_F:
            {
                chip8Components.getKeypad()[0xE] = new uInt8((short) 1);
            } break;

            case KeyEvent.VK_V:
            {
                chip8Components.getKeypad()[0xF] = new uInt8((short) 1);
            } break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
            {
                //quit = true;
            } break;

            case KeyEvent.VK_X:
            {
                chip8Components.getKeypad()[0] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_1:
            {
                chip8Components.getKeypad()[1] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_2:
            {
                chip8Components.getKeypad()[2] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_3:
            {
                chip8Components.getKeypad()[3] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_Q:
            {
                chip8Components.getKeypad()[4] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_W:
            {
                chip8Components.getKeypad()[5] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_E:
            {
                chip8Components.getKeypad()[6] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_A:
            {
                chip8Components.getKeypad()[7] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_S:
            {
                chip8Components.getKeypad()[8] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_D:
            {
                chip8Components.getKeypad()[9] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_Z:
            {
                chip8Components.getKeypad()[0xA] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_C:
            {
                chip8Components.getKeypad()[0xB] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_4:
            {
                chip8Components.getKeypad()[0xC] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_R:
            {
                chip8Components.getKeypad()[0xD] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_F:
            {
                chip8Components.getKeypad()[0xE] = new uInt8((short) 0);
            } break;

            case KeyEvent.VK_V:
            {
                chip8Components.getKeypad()[0xF] = new uInt8((short) 0);
            } break;
        }
    }
}