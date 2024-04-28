package Main.Frame;

public enum ImagePathEnum {
    DEFAULT("src/Main/Resources/Cells/Default/"),
    FOREST("src/Main/Resources/Cells/Forest/"),
    OCEAN("src/Main/Resources/Cells/Ocean/"),
    FLAME("src/Main/Resources/Cells/Flame/");

    private final String Path;
    private ImagePathEnum(String path) {
        Path = path;
    }
    public String getPath() {
        return Path;
    }
}
