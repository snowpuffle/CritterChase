package models.levels;

// LevelAssets Stores the Image Paths Used by a Level
public record LevelAssets(String background, String player, String food, String enemy, String wall1, String wall2,
                String exit, String weapon) {
}