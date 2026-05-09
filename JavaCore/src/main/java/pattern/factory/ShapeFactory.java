package pattern.factory;

/**
 * @author 12
 * Create By 下午2:08
 */
public class ShapeFactory {
    public static Shape createShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }

        switch (shapeType.toLowerCase()) {
            case "circle":
                return new Circle();
            case "rectangle":
                return new Rectangle();
            case "square":
                return new Square();
            default:
                return null;
        }
    }
}
