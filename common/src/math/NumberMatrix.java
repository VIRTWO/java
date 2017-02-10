package math;

/**
 * User: ANUJ Date: 9/9/11 Time: 11:09 PM
 */
public class NumberMatrix {

    /*
     * Do I care about the shit of -0.0 and +0.0. No, I do not. This class is based over double and no double handling
     * is done. Be careful while using it.
     */

    private int numOfRows = 0;
    private int numOfCols = 0;
    private double[][] matrix = null;

    public NumberMatrix(int numOfRows, int numOfCols) {
        this.numOfRows = numOfRows;
        this.numOfCols = numOfCols;
        matrix = new double[numOfRows][numOfCols];
    }

    public NumberMatrix(double[][] data) {
        if (data == null) {
            throw new IllegalArgumentException("NumberMatrix: Matrix cannot be created from null object.");
        }

        numOfCols = data.length;
        if (numOfCols == 0) {
            throw new IllegalArgumentException("NumberMatrix: Number of cols should be >= 1.");
        }
        numOfRows = data[0].length;
        if (numOfRows == 0) {
            throw new IllegalArgumentException("NumberMatrix: Number of rows should be >= 1.");
        }
        matrix = new double[numOfRows][numOfCols];
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                matrix[i][j] = data[i][j];
            }
        }
    }

    public double get(int rowIndex, int colIndex) {
        validateIndexRange(rowIndex, colIndex);
        return matrix[rowIndex][colIndex];
    }

    public void set(int rowIndex, int colIndex, double value) {
        validateIndexRange(rowIndex, colIndex);
        matrix[rowIndex][colIndex] = value;
    }

    public int columnRank() {
        return numOfRows;
    }

    public int rowRank() {
        return numOfCols;
    }

    public boolean isSquare() {
        return (numOfRows == numOfCols);
    }

    public boolean isIdentity() {
        boolean isIdentity = false;

        if (isSquare() == false) {
            return isIdentity;
        }

        check: for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; i < numOfCols; j++) {
                if (((i == j) && (matrix[i][j] != 1)) || ((i != j) && (matrix[i][j] != 0))) {
                    break check;
                }
            }
        }

        if (isIdentity == false) {
            isIdentity = (this.determinant() == 1) ? true : false;
        }

        return isIdentity;
    }

    public void add(NumberMatrix m) {
        if (m == null) {
            throw new IllegalArgumentException("NumberMatrix: Null matrix passed as argument.");
        }

        if ((m.numOfCols != numOfCols) || (m.numOfRows != numOfRows)) {
            throw new IllegalArgumentException("NumberMatrix: Only matrices of same dimensions can be added.");
        }

        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                matrix[i][j] = (matrix[i][j]) + (m.matrix[i][j]);
            }
        }
    }

    public void multiplyScalar(double scalar) {
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                matrix[i][j] = (matrix[i][j]) * scalar;
            }
        }
    }

    public NumberMatrix multiply(NumberMatrix m) {
        if (m == null) {
            throw new IllegalArgumentException("NumberMatrix: Null matrix passed as argument.");
        }

        if (numOfCols != m.numOfRows) {
            throw new IllegalArgumentException("NumberMatrix: #cols of Matrix A should be equal to #rows of Matrix B.");
        }

        NumberMatrix result = new NumberMatrix(numOfRows, m.numOfCols);

        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < m.numOfCols; j++) {
                result.matrix[i][j] = 0.0;
                for (int k = 0; k < numOfCols; k++) {
                    result.matrix[i][j] += matrix[i][k] * m.matrix[k][j];
                }
            }
        }
        return result;
    }

    public NumberMatrix transpose() {
        NumberMatrix resultNumberMatrix = new NumberMatrix(numOfCols, numOfRows);

        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                resultNumberMatrix.matrix[j][i] = matrix[i][j];
            }
        }

        return resultNumberMatrix;
    }

    public double determinant() {
        if (isSquare() == false) {
            throw new IllegalArgumentException("NumberMatrix: Determinant can only be calculated for square matrices.");
        }

        // We will use Gaussian Elimination over this
        // To save it from destruction we work on a new copy
        NumberMatrix m = clone();
        double det = 1.0;
        int numOfExchange = m.performGaussianElimination();

        for (int i = 0; i < m.numOfRows; i++) {
            det = det * m.matrix[i][i];
        }
        if ((numOfExchange % 2) != 0) {
            det = -1 * det;
        }

        return det;
    }

    public double trace() {
        if (isSquare() == false) {
            throw new IllegalArgumentException("NumberMatrix: Trace can only be calculated for square matrices.");
        }

        double trace = 0.0;

        for (int i = 0; i < numOfRows; i++) {
            trace = trace + matrix[i][i];
        }

        return trace;
    }

    public boolean isSymmetric() {
        NumberMatrix mCopy = clone();
        return this.equals(mCopy.transpose());
    }

    public static NumberMatrix add(NumberMatrix m1, NumberMatrix m2) {
        if ((m1 == null) || (m2 == null)) {
            throw new IllegalArgumentException("NumberMatrix: Null matrix passed as argument.");
        }

        if ((m1.numOfCols != m2.numOfCols) || (m1.numOfRows != m2.numOfRows)) {
            throw new IllegalArgumentException("NumberMatrix: Only matrices of same dimensions can be added.");
        }

        NumberMatrix resultNumberMatrix = new NumberMatrix(m1.numOfRows, m1.numOfCols);
        for (int i = 0; i < m1.numOfRows; i++) {
            for (int j = 0; j < m1.numOfCols; j++) {
                resultNumberMatrix.matrix[i][j] = (m1.matrix[i][j]) + (m2.matrix[i][j]);
            }
        }
        return resultNumberMatrix;
    }

    public static NumberMatrix multiplyScalar(NumberMatrix m, double scalar) {
        NumberMatrix resultNumberMatrix = new NumberMatrix(m.numOfRows, m.numOfCols);
        for (int i = 0; i < m.numOfRows; i++) {
            for (int j = 0; j < m.numOfCols; j++) {
                resultNumberMatrix.matrix[i][j] = (m.matrix[i][j]) * scalar;
            }
        }
        return resultNumberMatrix;
    }

    public static double determinant(NumberMatrix m) {
        if (m.isSquare() == false) {
            throw new IllegalArgumentException("NumberMatrix: Determinant can only be calculated for square matrices.");
        }

        m.clone();
        double det = 1.0;
        int numOfExchange = m.performGaussianElimination();

        for (int i = 0; i < m.numOfRows; i++) {
            det = det * m.matrix[i][i];
        }
        if ((numOfExchange % 2) != 0) {
            det = -1 * det;
        }

        return det;
    }

    public static double trace(NumberMatrix m) {
        if (m.isSquare() == false) {
            throw new RuntimeException("NumberMatrix: Trace can only be calculated for square matrices.");
        }

        double trace = 0.0;

        for (int i = 0; i < m.numOfRows; i++) {
            trace = trace + m.matrix[i][i];
        }

        return trace;
    }

    public static boolean isSymmetric(NumberMatrix m) {
        NumberMatrix mCopy = m.clone();
        return m.equals(mCopy.transpose());
    }

    public static boolean isIdentity(NumberMatrix m) {
        boolean isIdentity = false;

        if (m.isSquare() == false) {
            return isIdentity;
        }

        check: for (int i = 0; i < m.numOfRows; i++) {
            for (int j = 0; i < m.numOfCols; j++) {
                if (((i == j) && (m.matrix[i][j] != 1)) || ((i != j) && (m.matrix[i][j] != 0))) {
                    break check;
                }
            }
        }

        if (isIdentity == false) {
            isIdentity = (m.determinant() == 1) ? true : false;
        }

        return isIdentity;
    }

    public static NumberMatrix getRandomMatrix(int numOfRows, int numOfCols) {
        NumberMatrix numberMatrix = new NumberMatrix(numOfRows, numOfCols);
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                numberMatrix.set(i, j, Math.random() * 10);
            }
        }
        return numberMatrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                sb.append(matrix[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean equals(NumberMatrix m) {
        if ((numOfCols != m.numOfCols) || (numOfRows != m.numOfRows)) {
            return false;
        }

        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                if (matrix[i][j] != m.matrix[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public NumberMatrix clone() {
        return new NumberMatrix(matrix);

    }

    private void validateIndexRange(int rowIndex, int colIndex) {
        if ((rowIndex >= numOfRows) || (colIndex >= numOfCols) || (rowIndex < 0) || (colIndex < 0)) {
            throw new RuntimeException("NumberMatrix: Invalid range of index.");
        }
    }

    // Returns the number of row exchanges made to convert the matrix
    // It converts the matrix to upper triangular matrix by Gaussian Elimination
    private int performGaussianElimination() {
        if (isSquare() == false) {
            throw new RuntimeException("NumberMatrix: Gaussian Elimination can only be performed on square matrices.");
        }

        int numOfExchange = 0;

        for (int i = 0; i < (numOfRows - 1); i++) {
            // Checking rows 0, then 1, then 2
            // for all the rows i.e. i+j we need to make col elements zero where col is again the current i
            // get current element
            double curElem = matrix[i][i];
            // if curElement is 0 then we need to find a row that is having non-zero element in same col.
            if (curElem == 0.0) {
                exchangeLoop: for (int l = i; l < numOfRows; l++) {
                    if (matrix[l][i] != 0) {
                        curElem = matrix[l][i];
                        exchangeRows(i, l);
                        numOfExchange++;
                        System.out.println(toString());
                        break exchangeLoop;
                    }
                }
                if (curElem == 0.0) {
                    // curElem is still zero that means no row below has non-zero elem
                    // No point in doing anything for this col
                    continue;
                }
            }

            for (int j = i + 1; j < numOfRows; j++) {
                // here j points to rows below ith row
                // if the element under consideration is already zero we do not do anything
                if (matrix[j][i] == 0.0) {
                    continue;
                }

                // get multiplication factor
                double multiFactor = matrix[j][i] / curElem;
                // subtract current row(i) * multiFactor from below row(j) col-wise for col starting row i
                for (int k = i; k < numOfCols; k++) {
                    matrix[j][k] = matrix[j][k] - (matrix[i][k] * multiFactor);
                }
            }

        }

        return numOfExchange;
    }

    private void exchangeRows(int rowIndex1, int rowIndex2) {
        double[] dummy = matrix[rowIndex1];
        matrix[rowIndex1] = matrix[rowIndex2];
        matrix[rowIndex2] = dummy;
    }
}
