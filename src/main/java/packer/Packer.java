package packer;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class Packer {
    public static void main (String[] args) throws Exception {
        TexturePacker.process("src/main/resources/images/icons", "src/main/resources/images/icons", "icons");
    }
}