import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Login extends JFrame implements ActionListener {

    JTextField text;
    JButton Next, back;

    Login() {
        setTitle("Quiz App - Login");
        setSize(750, 550); // Fixed size
        setLocationRelativeTo(null); // Center the frame on the screen
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setResizable(false);
        
        JLabel heading = new JLabel("QUIZ TEST");
        heading.setBounds(270, 60, 300, 45);
        heading.setFont(new Font("Broadway", Font.BOLD, 40));
        heading.setForeground(new Color(22, 99, 54));
        add(heading);
    
        JLabel name = new JLabel("Enter Your Name");
        name.setBounds(290, 130, 300, 40);
        name.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        name.setForeground(new Color(22, 99, 54));
        add(name);
    
        text = new JTextField();
        text.setBounds(220, 210, 300, 40);
        text.setFont(new Font("Times New Roman", Font.BOLD, 18));
        add(text);
    
        Next = new JButton("Next");
        Next.setBounds(240, 300, 120, 40);
        Next.setBackground(new Color(22, 99, 54));
        Next.setForeground(Color.WHITE);
        Next.addActionListener(this);
        add(Next);
    
        back = new JButton("Exit");
        back.setBounds(380, 300, 120, 40);
        back.setBackground(new Color(22, 99, 54));
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);
    
        setVisible(true); // Only setting visibility here
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Next) {
            String name = text.getText();
            setVisible(false); // Hide the Login frame
            new Quiz(name); // Create and show the Quiz frame
        } else if (e.getSource() == back) {
            System.exit(0); // Exit the application
        }
    }

    public static void main(String[] args) {
        new Login(); // Create and display the Login frame
    }
}

class Quiz extends JFrame implements ActionListener {

    String[][] questions = new String[10][5];
    String[][] answers = new String[10][2];
    String[][] useranswers = new String[10][1];
    JLabel qno, question;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup groupoptions;
    JButton next, submit;

    public static int timer = 15;
    public static int ans_given = 0;
    public static int count = 0;
    public static int score = 0;

    String name;

    Quiz(String name) {
        this.name = name;
        count = 0;
        score = 0;
        ans_given = 0;

        setTitle("Quiz App - Quiz");
        setSize(750, 550); // Fixed size
        setLocation(400, 150);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setResizable(false);

        qno = new JLabel();
        qno.setBounds(50, 100, 50, 30);
        qno.setFont(new Font("Mongolian Baiti", Font.PLAIN, 24));
        add(qno);

        question = new JLabel();
        question.setBounds(100, 100, 600, 60); // Increased height for longer questions
        question.setFont(new Font("Mongolian Baiti", Font.PLAIN, 24));
        question.setVerticalAlignment(SwingConstants.TOP); // Align text to the top
        add(question);

        setupQuestions();

        opt1 = new JRadioButton();
        opt1.setBounds(100, 180, 600, 30);
        opt1.setBackground(Color.WHITE);
        opt1.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(opt1);

        opt2 = new JRadioButton();
        opt2.setBounds(100, 220, 600, 30);
        opt2.setBackground(Color.WHITE);
        opt2.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(opt2);

        opt3 = new JRadioButton();
        opt3.setBounds(100, 260, 600, 30);
        opt3.setBackground(Color.WHITE);
        opt3.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(opt3);

        opt4 = new JRadioButton();
        opt4.setBounds(100, 300, 600, 30);
        opt4.setBackground(Color.WHITE);
        opt4.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(opt4);

        groupoptions = new ButtonGroup();
        groupoptions.add(opt1);
        groupoptions.add(opt2);
        groupoptions.add(opt3);
        groupoptions.add(opt4);

        next = new JButton("Next");
        next.setBounds(240, 400, 100, 40);
        next.setFont(new Font("Tahoma", Font.PLAIN, 18));
        next.setBackground(new Color(22, 99, 54));
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        add(next);

        submit = new JButton("Submit");
        submit.setBounds(380, 400, 100, 40);
        submit.setForeground(Color.BLACK);
        submit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        submit.setBackground(new Color(255, 215, 0));
        submit.addActionListener(this);
        submit.setEnabled(false);
        add(submit);

        start(count);
        setVisible(true); // Only setting visibility here
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            ans_given = 1;
            recordAnswer();
            if (count == 8) {
                next.setEnabled(false);
                submit.setEnabled(true);
            }
            count++;
            start(count);
        } else if (ae.getSource() == submit) {
            ans_given = 1;
            recordAnswer();
            calculateScore();
            setVisible(false);
            new Score(name, score); // Create and show the Score frame
        }
    }

    public void start(int count) {
        qno.setText("" + (count + 1) + ". ");
        
        // Use HTML to handle long text
        question.setText("<html>" + questions[count][0] + "</html>");
        
        opt1.setText(questions[count][1]);
        opt1.setActionCommand(questions[count][1]);
        
        opt2.setText(questions[count][2]);
        opt2.setActionCommand(questions[count][2]);
        
        opt3.setText(questions[count][3]);
        opt3.setActionCommand(questions[count][3]);
        
        opt4.setText(questions[count][4]);
        opt4.setActionCommand(questions[count][4]);
        
        groupoptions.clearSelection();
    }

    public void recordAnswer() {
        if (groupoptions.getSelection() == null) {
            useranswers[count][0] = "";
        } else {
            useranswers[count][0] = groupoptions.getSelection().getActionCommand();
        }
    }

    public void calculateScore() {
        for (int i = 0; i < useranswers.length; i++) {
            if (useranswers[i][0].equals(answers[i][1])) {
                score += 10;
            }
        }
    }

    public void setupQuestions() {
        questions[0][0] = "Number of primitive data types in Java are.";
        questions[0][1] = "6";
        questions[0][2] = "7";
        questions[0][3] = "8";
        questions[0][4] = "9";

        answers[0][1] = "8";

        questions[1][0] = "What is the size of float and double in java.?";
        questions[1][1] = "32 and 64";
        questions[1][2] = "32 and 32";
        questions[1][3] = "64 and 64";
        questions[1][4] = "64 and 32";
        answers[1][1] = "32 and 64";

        questions[2][0] = "Automatic type conversion is possible in which of the possible cases?";
        questions[2][1] = "Byte to int";
        questions[2][2] = "Int to Long";
        questions[2][3] = "Long to int";
        questions[2][4] = "Short to int";
        
        answers[2][1] = "Int to Long";
        
        questions[3][0] = "When an array is passed to a method, what does the method receive?";
        questions[3][1] = "The reference of the array";
        questions[3][2] = "A copy of the array";
        questions[3][3] = "Length of the array";
        questions[3][4] = "Copy of first element";
        
        answers[3][1] = "The reference of the array";
        
        questions[4][0] = "Arrays in java are.?";
        questions[4][1] = "Object References";
        questions[4][2] = "Objects";
        questions[4][3] = "Primitive data type";
        questions[4][4] = "None of these";
        
        answers[4][1] = "Object References";
        
        questions[5][0] = "What is the default value of boolean variable?";
        questions[5][1] = "True";
        questions[5][2] = "False";
        questions[5][3] = "0";
        questions[5][4] = "1";
        
        answers[5][1] = "False";
        
        questions[6][0] = "Which of the following is used to find and fix bugs in the java programs?";
        questions[6][1] = "JVM";
        questions[6][2] = "JRE";
        questions[6][3] = "JDB";
        questions[6][4] = "JAVADOC";
        
        answers[6][1] = "JDB";
        
        questions[7][0] = "In which of the following places can a 'final' keyword be used?";
        questions[7][1] = "Variable";
        questions[7][2] = "Method";
        questions[7][3] = "Class";
        questions[7][4] = "All of the above";
        
        answers[7][1] = "All of the above";
        
        questions[8][0] = "What is the extension of java code files?";
        questions[8][1] = ".java";
        questions[8][2] = ".class";
        questions[8][3] = ".js";
        questions[8][4] = ".jv";
        
        answers[8][1] = ".java";
        
        questions[9][0] = "Which of the following is not a Java feature?";
        questions[9][1] = "Object-Oriented";
        questions[9][2] = "Use of pointers";
        questions[9][3] = "Portable";
        questions[9][4] = "Dynamic";
        
        answers[9][1] = "Use of pointers";
    }
}

class Score extends JFrame {

    Score(String name, int score) {
        setTitle("Quiz App-Score");
        setSize(750, 550); // Fixed size
        setLocation(400, 150);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setResizable(false);

            JLabel heading = new JLabel("Thank you " + name + " for Playing QUIZ Test");
            heading.setBounds(120, 80, 700, 30);
            heading.setFont(new Font("Tahoma", Font.BOLD, 26));
            add(heading);
    
            JLabel scoreLabel = new JLabel("Your Score is " + score);
            scoreLabel.setBounds(250, 200, 300, 30);
            scoreLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
            add(scoreLabel);
    
            JButton exit = new JButton("Back");
            exit.setBounds(280, 270, 150, 40);
            exit.setFont(new Font("Mongolian Baiti", Font.PLAIN, 18));
            exit.setBackground(new Color(22, 99, 54));
            exit.setForeground(Color.WHITE);
            add(exit);
    
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    new Login();
                }
            });

            setVisible(true);
        }
}
