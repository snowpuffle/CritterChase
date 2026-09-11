package models.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import models.levels.Level;
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
                assertTrue(manager.getGameSession().isStarted());

                // Verify First Level Loaded
                assertEquals(LevelConfig.getStartingLevel(), manager.getCurrentLevel().getLevelNumber());

                // Verify Current Level Exists
                assertNotNull(manager.getCurrentLevel());
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
                assertEquals(LevelConfig.getStartingLevel(), level.getLevelNumber());
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
                assertEquals(GameTurnResult.MOVED, result);
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
                assertEquals(GameTurnResult.INVALID_MOVE, result);
        }

        // Test Player Movement Before Game Starts
        @Test
        void playTurnWithoutActiveLevelReturnsInvalidMove() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Attempt To Move Before Starting Game
                GameTurnResult result = manager.playTurn(Direction.RIGHT);

                // Verify No Valid Move Was Processed
                assertEquals(GameTurnResult.INVALID_MOVE, result);
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

                // Find Walkable Position Before Exit
                Object[] exitApproach = findExitApproach(level);

                // Get Player Position Before Exit
                int[] position = (int[]) exitApproach[0];

                // Get Direction Toward Exit
                Direction direction = (Direction) exitApproach[1];

                // Place Player Before Exit
                level.getPlayer().setPosition(position[0], position[1]);

                // Move Player Onto Exit
                GameTurnResult result = manager.playTurn(direction);

                // Verify Final Level Was Won
                assertEquals(GameTurnResult.GAME_WON, result);

                // Verify Level Score Was Transferred
                assertEquals(30, manager.getScore());
        }

        // Test Final Level Score Transfers Only Once
        @Test
        void completingFinalLevelTransfersScoreOnlyOnce() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Start Game
                manager.startGame();

                // Create Final Level
                var finalLevel = LevelFactory.createLevel(LevelConfig.getFinalLevel(), new Score());

                // Set Final Level As Current Level
                manager.getGameSession().setCurrentLevel(finalLevel);

                // Add Points To Final Level
                finalLevel.getScore().addPoints(40);

                // Find Walkable Position Before Exit
                Object[] exitApproach = findExitApproach(finalLevel);

                // Get Player Position Before Exit
                int[] position = (int[]) exitApproach[0];

                // Get Direction Toward Exit
                Direction direction = (Direction) exitApproach[1];

                // Place Player Before Exit
                finalLevel.getPlayer().setPosition(position[0], position[1]);

                // Move Player Onto Final Exit
                GameTurnResult result = manager.playTurn(direction);

                // Verify Game Was Won
                assertEquals(GameTurnResult.GAME_WON, result);

                // Verify Score Was Transferred
                assertEquals(40, manager.getScore());

                // Attempt Another Turn After Game Won
                GameTurnResult secondResult = manager.playTurn(direction);

                // Verify Turn Was Rejected
                assertEquals(GameTurnResult.INVALID_MOVE, secondResult);

                // Verify Score Was Not Added Again
                assertEquals(40, manager.getScore());
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
                assertEquals(100, manager.getScore());
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
                assertEquals(0, manager.getScore());

                // Verify Game Returned To First Level
                assertEquals(LevelConfig.getStartingLevel(), manager.getCurrentLevel().getLevelNumber());

                // Verify Game Session Started
                assertTrue(manager.getGameSession().isStarted());
        }

        // Test Game Over Detection
        @Test
        void gameOverIsFalseWhenPlayerIsAlive() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Start New Game Session
                manager.startGame();

                // Verify Player Is Not Game Over
                assertFalse(manager.isGameOver());
        }

        // Test Game Won Detection
        @Test
        void gameWonIsFalseBeforeFinalLevelIsComplete() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Start New Game Session
                manager.startGame();

                // Verify Game Has Not Been Won
                assertFalse(manager.isGameWon());
        }

        // Test Total Maximum Score
        @Test
        void getTotalMaxScoreReturnsConfiguredMaximum() {

                // Create GameManager Instance
                GameManager manager = new GameManager();

                // Get Total Maximum Score
                int totalMaxScore = manager.getTotalMaxScore();

                // Verify Total Maximum Score Matches Configuration
                assertEquals(LevelConfig.getTotalMaxScore(), totalMaxScore);
        }

        // Find Exit Position On Game Board
        private int[] findExitPosition(models.levels.Level level) {

                // Get Game Board
                var gameBoard = level.getGameBoard();

                // Search Each Row For Exit
                for (int row = 0; row < GameBoardConfig.HEIGHT; row++) {

                        // Search Each Column For Exit
                        for (int col = 0; col < GameBoardConfig.WIDTH; col++) {

                                // Get Game Object At Current Position
                                var object = gameBoard.getGameObjectAt(row, col);

                                // Check If Current Object Is Exit
                                if (object != null && object.getType() == GameObjectType.EXIT) {

                                        // Return Exit Row And Column
                                        return new int[] { row, col };
                                }
                        }
                }

                // Throw Error When Exit Cannot Be Found
                throw new IllegalStateException(
                                "Exit Not Found.");
        }

        // Find Walkable Position And Direction Before Exit
        private Object[] findExitApproach(Level level) {

                // Get Exit Position
                int[] exit = findExitPosition(level);

                // Get Game Board
                var gameBoard = level.getGameBoard();

                // Check Position To Left Of Exit
                if (isWalkable(gameBoard, exit[0], exit[1] - 1)) {

                        // Return Position And Right Direction
                        return new Object[] { new int[] { exit[0], exit[1] - 1 }, Direction.RIGHT };
                }

                // Check Position To Right Of Exit
                if (isWalkable(gameBoard, exit[0], exit[1] + 1)) {

                        // Return Position And Left Direction
                        return new Object[] { new int[] { exit[0], exit[1] + 1 }, Direction.LEFT };
                }

                // Check Position Above Exit
                if (isWalkable(gameBoard, exit[0] - 1, exit[1])) {

                        // Return Position And Down Direction
                        return new Object[] { new int[] { exit[0] - 1, exit[1] }, Direction.DOWN };
                }

                // Check Position Below Exit
                if (isWalkable(gameBoard, exit[0] + 1, exit[1])) {

                        // Return Position And Up Direction
                        return new Object[] { new int[] { exit[0] + 1, exit[1] }, Direction.UP };
                }

                // Throw Error When Exit Cannot Be Reached
                throw new IllegalStateException("Exit Has No Walkable Approach.");
        }

        // Check If Board Position Is Walkable
        private boolean isWalkable(GameBoard gameBoard, int row, int col) {

                // Reject Positions Outside Game Board
                if (!gameBoard.isValidPosition(row, col)) {
                        return false;
                }

                // Get Game Object At Position
                var object = gameBoard.getGameObjectAt(row, col);

                // Allow Empty Positions
                if (object == null) {
                        return true;
                }

                // Allow Food Positions
                if (object.getType() == GameObjectType.FOOD) {
                        return true;
                }

                // Allow Exit Positions
                return object.getType() == GameObjectType.EXIT;
        }
}