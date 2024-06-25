import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Question {
    private String question;
    private String[] options;
    private String correctAnswer;
    private String hint;

    public Question(String question, String[] options, String correctAnswer, String hint) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.hint = hint;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getHint() {
        return hint;
    }
}

public class QuizGame extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L; // Menambahkan serialVersionUID
    private static final Question[] questions = {
        new Question("Apa yang membuatmu merasa hampa, tetapi juga membuatmu merenung?", new String[]{"Kegagalan", "Patah hati", "Penantian", "Kesuksesan"}, "Patah hati", "Terjadi ketika cinta berakhir, tapi meninggalkan pelajaran berharga."),
new Question("Siapa yang selalu berada di pikiranmu, tetapi pergi tanpa meninggalkan jejak?", new String[]{"Sahabat", "Guru","Mantan", "Tetangga"}, "Mantan", "Seseorang yang pernah berarti banyak, tapi sekarang hanya kenangan."),
new Question("Apa yang membuat hati sakit, tetapi membuatmu lebih kuat?", new String[]{"Kesedihan", "Pengalaman", "Kebahagiaan", "Kemarahan"}, "Pengalaman", "Pembelajaran dari kesalahan dan rintangan."),
new Question("Apa yang selalu menghantuimu di malam hari, tapi menghilang saat mata terbuka?", new String[]{"Mimpi buruk", "Bayangan", "Kenangan", "Suara"}, "Kenangan", "Memori tentang momen-momen yang menyakitkan."),
new Question("Siapa yang dapat membuatmu tertawa saat sedih, tapi juga membuatmu marah?", new String[]{"Adik", "Teman", "Orang tua", "Guru"}, "Teman", "Ada saat-saatnya membawa keceriaan, tapi kadang juga bisa menjadi penyebab frustrasi."),
new Question("Apa yang membuatmu ingin menangis saat mendengarnya, tetapi juga membuatmu ingin mengabaikannya?", new String[]{"Cerita lucu", "Lagu-lagu sedih", "Berita baik", "Suara alam"}, "Lagu-lagu sedih", "Lirik yang menyentuh, tetapi terkadang lebih baik dihindari."),
new Question("Apa yang selalu mengganggumu, bahkan saat sedang fokus?", new String[]{"Alarm", "Telepon", "Pikiran negatif", "Lampu"}, "Pikiran negatif", "Berulang kali muncul tanpa diundang."),
new Question("Siapa yang membuatmu merasa rendah diri, tapi juga menjadi sumber motivasi?", new String[]{"Teman", "Saingan", "Keluarga", "Idola"}, "Saingan", "Bersaing untuk menjadi yang terbaik, tetapi juga menimbulkan perasaan tidak cukup."),
new Question("Apa yang selalu mengganggumu saat sedang berusaha melupakan?", new String[]{"Kebisingan", "Tugas", "Kenangan manis", "Janji"}, "Kenangan manis", "Momen-momen indah yang sulit dilupakan."),
new Question("Siapa yang membuatmu tersenyum hari ini, tapi membuatmu menangis semalam?", new String[]{"Sahabat", "Orang tua", "Orang yang kamu cintai", "Tetangga"}, "Orang yang kamu cintai", "Perubahan emosi yang disebabkan oleh hubungan yang rumit.")

        // Tambahkan pertanyaan lainnya di sini
    };

    private int currentQuestionIndex;
    private int score;
    private JLabel questionLabel;
    private JButton[] optionButtons;
    private JButton hintButton;
    private JPanel quizPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public QuizGame() {
        super("Kuis Tebak-Tebakan Ngeselin");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        startPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>Selamat Datang di Kuis Tebak-Tebakan Ngeselin!</div></html>", JLabel.CENTER);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startPanel.add(welcomeLabel);
        startPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton startButton = new JButton("Mulai Kuis");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setPreferredSize(new Dimension(150, 50)); // Mengatur ukuran tombol
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "quizPanel");
                showQuestion();
            }
        });
        startPanel.add(startButton);

        quizPanel = new JPanel();
        quizPanel.setLayout(new BorderLayout(10, 10));

        questionLabel = new JLabel("", JLabel.CENTER);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Menambahkan padding lebih besar
        quizPanel.add(questionLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        optionButtons = new JButton[4];
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            optionButtons[i].setPreferredSize(new Dimension(150, 50)); // Mengatur ukuran tombol
            optionButtons[i].setMargin(new Insets(10, 10, 10, 10)); // Mengatur margin tombol
            optionButtons[i].addActionListener(this);
            buttonPanel.add(optionButtons[i]);
        }
        quizPanel.add(buttonPanel, BorderLayout.CENTER);

        hintButton = new JButton("Hint");
        hintButton.setFont(new Font("Arial", Font.PLAIN, 14));
        hintButton.addActionListener(this);
        quizPanel.add(hintButton, BorderLayout.SOUTH);

        mainPanel.add(startPanel, "startPanel");
        mainPanel.add(quizPanel, "quizPanel");

        add(mainPanel);

        currentQuestionIndex = 0;
        score = 0;
    }

    private void showQuestion() {
        if (currentQuestionIndex < questions.length) {
            Question currentQuestion = questions[currentQuestionIndex];
            questionLabel.setText("<html><div style='text-align: center;'>" + currentQuestion.getQuestion() + "</div></html>");
            String[] options = currentQuestion.getOptions();
            for (int i = 0; i < optionButtons.length; i++) {
                optionButtons[i].setText(options[i]);
                optionButtons[i].setEnabled(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Permainan selesai! Skor Anda: " + score + "/" + questions.length);
            int choice = JOptionPane.showConfirmDialog(this, "Main lagi?", "Kuis Selesai", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                resetGame();
                cardLayout.show(mainPanel, "startPanel");
            } else {
                System.exit(0);
            }
        }
    }

    private void resetGame() {
        currentQuestionIndex = 0;
        score = 0;
        for (JButton button : optionButtons) {
            button.setText("");
            button.setEnabled(true);
        }
        questionLabel.setText("");
    }

    private void checkAnswer(String answer) {
        Question currentQuestion = questions[currentQuestionIndex];
        if (answer.equalsIgnoreCase(currentQuestion.getCorrectAnswer())) {
            score++;
            JOptionPane.showMessageDialog(this, "Jawaban Anda benar!");
        } else {
            JOptionPane.showMessageDialog(this, "Jawaban Anda salah! Hint: " + currentQuestion.getHint());
        }
        currentQuestionIndex++;
        showQuestion();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == hintButton) {
            Question currentQuestion = questions[currentQuestionIndex];
            JOptionPane.showMessageDialog(this, "Hint: " + currentQuestion.getHint());
        } else {
            for (int i = 0; i < optionButtons.length; i++) {
                if (source == optionButtons[i]) {
                    checkAnswer(optionButtons[i].getText());
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                QuizGame game = new QuizGame();
                game.setVisible(true);
                game.showQuestion(); // Menampilkan pertanyaan pertama setelah UI ditampilkan
            }
        });
    }
}
