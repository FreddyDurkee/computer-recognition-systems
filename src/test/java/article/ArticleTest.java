package article;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArticleTest {

    @Test
    void addLabel() {
        // Given
        Article example = new Article();
        assertEquals(0, example.getLabel().size());
        String newLabel = "New Label";

        // When
        example.addLabel(newLabel);

        // Then
        assertEquals(1, example.getLabel().size());
    }

    @Test
    void clearLabels() {
        // Given
        Article example = new Article();
        String newLabel = "New Label";
        example.addLabel(newLabel);
        assertEquals(1, example.getLabel().size());

        // When
        example.clearLabels();

        // Then
        assertEquals(0, example.getLabel().size());
    }

    @Test
    void getTextAndTitle() {
        // Given
        Article example = new Article();
        String newLabel = "New Label";
        example.addLabel(newLabel);
        example.setTitle("Example Title");
        example.setText("Example Text");

        // When
        String actual = example.getTextAndTitle();

        // Then
        assertEquals("Example Title\nExample Text", actual);
    }
}