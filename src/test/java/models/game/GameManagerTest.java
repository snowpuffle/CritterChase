package models.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import models.levels.LevelConfig;
import models.levels.LevelFactory;
import models.objects.Score;
import models.utils.Direction;

// GameManagerTest Tests GameManager Core Game Rules
// - Starts A New Game At First Level
// - Loads Current Level
// - Processes Valid Player Movement
// - Rejects Invalid Player Movement
// - Rejects Movement Before Game Starts
// - Transfers Level Score To Total Score
// - Does Not Add Level Score More Than Once
// - Restarts Game And Resets Score
// - Detects Game Over
// - Detects Game Won
// - Returns Total Maximum Score
class GameManagerTest {

        // Test GameManager Initialization
        @Test
        void startGameStartsAtFirstLevel() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Start New Game Session
                manager.startGame();

                // Verify Game Session Started
                assertTrue(
                                manager.getGameSession().isStarted());

                // Verify First Level Loaded
                assertEquals(
                                LevelConfig.getStartingLevel(),
                                manager.getCurrentLevel().getLevelNumber());

                // Verify Current Level Exists
                assertNotNull(
                                manager.getCurrentLevel());
        }

        // Test Current Level Access
        @Test
        void getCurrentLevelReturnsActiveLevel() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Start New Game Session
                manager.startGame();

                // Get Current Level
                var level = manager.getCurrentLevel();

                // Verify Current Level Exists
                assertNotNull(level);

                // Verify Level Number Matches Configuration
                assertEquals(
                                LevelConfig.getStartingLevel(),
                                level.getLevelNumber());
        }

        // Test Valid Player Movement
        @Test
        void playTurnProcessesValidMovement() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Start New Game Session
                manager.startGame();

                // Process Valid Player Movement
                GameTurnResult result = manager.playTurn(Direction.RIGHT);

                // Verify Movement Was Successful
                assertEquals(
                                GameTurnResult.MOVED,
                                result);
        }

        // Test Invalid Player Movement
        @Test
        void playTurnRejectsInvalidMovement() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Start New Game Session
                manager.startGame();

                // Attempt Invalid Player Movement
                GameTurnResult result = manager.playTurn(Direction.LEFT);

                // Verify Movement Was Rejected
                assertEquals(
                                GameTurnResult.INVALID_MOVE,
                                result);
        }

        // Test Player Movement Before Game Starts
        @Test
        void playTurnWithoutActiveLevelReturnsInvalidMove() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Attempt To Move Before Starting Game
                GameTurnResult result = manager.playTurn(Direction.RIGHT);

                // Verify No Valid Move Was Processed
                assertEquals(
                                GameTurnResult.INVALID_MOVE,
                                result);
        }

        // Test Level Score Transfers To Total Score
        @Test
        void completingLevelTransfersScoreToTotalScore() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Start New Game Session
                manager.startGame();

                // Get Current Level
                var level = manager.getCurrentLevel();

                // Add Points To Current Level
                level.getScore().addPoints(30);

                // Place Player Before Exit
                // Level 1 Exit Is At Row 13, Column 14
                level.getPlayer().setPosition(13, 13);

                // Move Player Onto Exit
                GameTurnResult result = manager.playTurn(Direction.RIGHT);

                // Verify Level Was Completed
                assertEquals(
                                GameTurnResult.LEVEL_COMPLETE,
                                result);

                // Verify Level Score Was Transferred
                assertEquals(
                                30,
                                manager.getScore());

                // Verify Next Level Loaded
                assertEquals(
                                2,
                                manager.getCurrentLevel().getLevelNumber());
        }

        // Test Final Level Score Transfers Only Once
        @Test
        void completingFinalLevelTransfersScoreOnlyOnce() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Start Game
                manager.startGame();

                // Create Final Level
                var finalLevel = LevelFactory.createLevel(
                                LevelConfig.getFinalLevel(),
                                new Score());

                // Set Final Level As Current Level
                manager.getGameSession().setCurrentLevel(finalLevel);

                // Add Points To Final Level
                finalLevel.getScore().addPoints(40);

                // Place Player Before Exit
                // Level 4 Exit Is At Row 13, Column 14
                finalLevel.getPlayer().setPosition(13, 13);

                // Move Player Onto Final Exit
                GameTurnResult result = manager.playTurn(Direction.RIGHT);

                // Verify Game Was Won
                assertEquals(
                                GameTurnResult.GAME_WON,
                                result);

                // Verify Score Was Transferred
                assertEquals(
                                40,
                                manager.getScore());

                // Attempt Another Turn After Game Won
                GameTurnResult secondResult = manager.playTurn(Direction.RIGHT);

                // Verify Turn Was Rejected
                assertEquals(
                                GameTurnResult.INVALID_MOVE,
                                secondResult);

                // Verify Score Was Not Added Again
                assertEquals(
                                40,
                                manager.getScore());
        }

        // Test Score Addition Through Game Session
        @Test
        void addScoreIncreasesTotalScore() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Start New Game Session
                manager.startGame();

                // Add Points Through Game Session
                manager.getGameSession().addScore(100);

                // Verify Total Score Increased
                assertEquals(
                                100,
                                manager.getScore());
        }

        // Test Game Restart
        @Test
        void restartGameResetsScore() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Start New Game Session
                manager.startGame();

                // Add Points Through Game Session
                manager.getGameSession().addScore(100);

                // Start New Game Again
                manager.startGame();

                // Verify Score Reset
                assertEquals(
                                0,
                                manager.getScore());

                // Verify Game Returned To First Level
                assertEquals(
                                LevelConfig.getStartingLevel(),
                                manager.getCurrentLevel().getLevelNumber());

                // Verify Game Session Started
                assertTrue(
                                manager.getGameSession().isStarted());
        }

        // Test Game Over Detection
        @Test
        void gameOverIsFalseWhenPlayerIsAlive() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Start New Game Session
                manager.startGame();

                // Verify Player Is Not Game Over
                assertFalse(
                                manager.isGameOver());
        }

        // Test Game Won Detection
        @Test
        void gameWonIsFalseBeforeFinalLevelIsComplete() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Start New Game Session
                manager.startGame();

                // Verify Game Has Not Been Won
                assertFalse(
                                manager.isGameWon());
        }

        // Test Total Maximum Score
        @Test
        void getTotalMaxScoreReturnsConfiguredMaximum() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Get Total Maximum Score
                int totalMaxScore = manager.getTotalMaxScore();

                // Verify Total Maximum Score Matches Configuration
                assertEquals(
                                LevelConfig.getTotalMaxScore(),
                                totalMaxScore);
        }
}