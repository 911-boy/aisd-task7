package task7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphFrame extends JFrame {
    private JTextArea inputArea;
    private JButton findCycleButton;
    private JLabel resultLabel;

    public GraphFrame() {
        setTitle("Поиск кратчайшего цикла в графе");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === Ввод графа ===
        inputArea = new JTextArea(10, 40);
        inputArea.setText("5 6\n0 1\n1 2\n2 3\n3 0\n1 3\n0 4\n");
        JScrollPane scrollPane = new JScrollPane(inputArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        findCycleButton = new JButton("Найти длину кратчайшего цикла");
        resultLabel = new JLabel("Результат: ");
        bottomPanel.add(findCycleButton);
        bottomPanel.add(resultLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        // === Ключевой обработчик: запуск поиска цикла ===
        findCycleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputArea.getText();
                try {
                    // 1 преобразуем текст в граф
                    Graph graph = GraphUtils.fromStr(input);
                    // 2запускаем поиск кратчайшего цикла
                    int cycleLen = GraphAlgorithms.shortestCycleLength(graph);
                    // 3 ыыводим результат
                    if (cycleLen == -1) {
                        resultLabel.setText("Результат: В графе нет циклов.");
                    } else {
                        resultLabel.setText("Результат: " + cycleLen);
                    }
                } catch (Exception ex) {
                    resultLabel.setText("Ошибка ввода");
                }
            }
        });
    }
} 