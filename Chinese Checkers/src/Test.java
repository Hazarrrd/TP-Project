

import board.Board;
import fields.Checker;
import fields.EmptyField;
import fields.Field;
import game.Colors;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by lenovo on 01.01.2018.
 */
public class Test extends TestCase {
    int side = 5;
    int width = (side-2)+2*side;
    int height = 4*side-3;

    Board board1 = null;

    public void setUp() {
        board1 = new Board(5, 6, 10);
    }

    public void testMoveIsLegal() {

        board1.isMoveLegal(1, 7, 2, 7, Colors.GREEN);
        Assert.assertEquals(false, board1.isMoveLegal(1, 7, 2, 7, Colors.GREEN));
        Assert.assertEquals(false, board1.isMoveLegal(1, 7, 1, 1, Colors.GREEN));

    }

    public void testDidPlayerWin() {
        board1.didPlayerWin(Colors.GREEN);
        Assert.assertSame(false, board1.didPlayerWin(Colors.GREEN));
    }

    public void testGiveNeighbourToArray() {
        assertNotNull("nie", board1.board);
    }

    public void testDoMove(){
        Field[][] b1 = new Field[width][height];

        for(int i=0;i<side;i++) {
            for (int j = 0; j < width; j++) {
                b1[i][j] = new EmptyField(i,j);
            }
        }

        boolean move = false;

        board1.board[4][7] = new Checker(Colors.GREEN, 4, 7, 1);
        b1[4][7]=board1.board[4][7];
        if(b1[4][7].kindOfField == 2) {
            board1.doMove(4,7,5,7);
            move = true;
        }

        Assert.assertTrue("false", move);

    }

    public void testFillerFirst(){
        Field[][] b1 = new Field[width][height];

        for(int i=0;i<side;i++) {
            for (int j = 0; j < width; j++) {
                b1[i][j] = new EmptyField(i,j);
            }
        }

        boolean fill = false;

        for(int i=0;i<side;i++) {
            for (int j = 0; j < width; j++) {
                if (b1[i][j].kindOfField == 1) {
                    board1.fillerFirst(0);
                    fill = true;
                }
            }
        }

        Assert.assertTrue("false", fill);
    }


    public void testArrayFirst() {
        assertNotNull("null", board1.arrayFirst());
    }

    public void testArraySecond() {assertNotNull("null", board1.arraySecond());}

    public void testArrayThird() {
        assertNotNull("null", board1.arrayThird());
    }

    public void testArrayFourth() {
        assertNotNull("null", board1.arrayFourth());
    }

    public void testArrayFifth() {
        assertNotNull("null", board1.arrayFifth());
    }

    public void testArraySixth() {
        assertNotNull("null", board1.arraySixth());
    }



    public void testCheckIfInTarget() {
        Field[][] b1 = new Field[width][height];

        for(int i=0;i<side;i++) {
            for (int j = 0; j < width; j++) {
                b1[i][j] = new EmptyField(i, j);
            }
        }
        board1.board[1][7]= new Checker(Colors.GREEN, 4, 7, 1);
        Field c1 = board1.board[1][7];

        //board1.checkIfInTarget(board1.board[1][7]);
        Assert.assertEquals(board1.checkIfInTriangle(c1,c1.target), board1.checkIfInTarget(c1));
    }

    public void testCheckIfInTriangle() {
        Field[][] b1 = new Field[width][height];

        for(int i=0;i<side;i++) {
            for (int j = 0; j < width; j++) {
                b1[i][j] = new EmptyField(i, j);
            }
        }

        Field c1 = b1[5][1];

        board1.checkIfInTriangle(c1, 1);
        Assert.assertEquals(board1.checkIfInTargetHelper(c1, board1.arrayFirst()), board1.checkIfInTriangle(c1, 1));
    }

    public void testCheckIfInTargetHelper() {
        
        boolean is = false;
        board1.board[3][7] = new Checker(Colors.GREEN, 3, 7, 1);
        board1.checkIfInTarget(board1.board[3][7]);
     
        if(board1.board[3][7].reachedTarget == true)
            is = true;
       
        Assert.assertTrue("m", is);

    }

    public void testMakeEmptyBoard() {
        boolean empty = false;

        board1.makeEmptyBoard(6);

        if(board1.board != null)
            empty = true;

        Assert.assertTrue("false", empty);
    }

    public void tearDown() {
        board1 = null;
    }

}
