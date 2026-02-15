package us.malfeasant.c64stuff.common;

public class Editor {
    public enum Type {
        CHAR(8, 8) {
            @Override
            protected int getOffset(int x, int y) {
                return y;
            }
        },
        SPRITE(24, 21) {
            @Override
            protected int getOffset(int x, int y) {
                return (x / 8) + y * 3;
            }
        },
        BITMAP(320, 200) {
            @Override
            protected int getOffset(int x, int y) {
                int cbx = x & -8; int cby = y & -8;
                int line = y & 7;
                return cbx + cby * 40 + line;
            }
        };

        public final int width;
        public final int height;

        Type(int fw, int fh) {
            width = fw;
            height = fh;
        }

        private void writePixel(boolean mc, int x, int y, int c, ArrayLike bytes) {
            var mask = mc ? 3 : 1;
            var shift = mc ? x & -2 : x;    // & -2 converts to multiple of 2
            c = (c & mask) << shift;
            mask = ~(mask << shift);
            var by = bytes.get(getOffset(x, y));
            by &= mask;
            by |= c;
            bytes.set(getOffset(x, y), c); 
        }
        protected abstract int getOffset(int x, int y);
    }

    private final Type editorType;

    public Editor(Type which) {
        editorType = which;
    }
}
