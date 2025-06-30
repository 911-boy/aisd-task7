package task7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class GraphDemoFrame extends JFrame {
    private JTextArea textAreaGraphFile;
    private JTextArea textAreaSystemOut;
    private JComboBox<String> comboBoxGraphType;
    private JButton buttonLoadGraphFromFile;
    private JButton buttonSaveGraphToFile;
    private JButton buttonCreateGraph;
    private JButton buttonFindShortestCycle;
    private JPanel panelGraphPainterContainer;
    private Graph graph = null;

    public GraphDemoFrame() {
        setTitle("Графы (лекционный GUI)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel panelGraphTab = new JPanel(new BorderLayout());
        tabbedPane.addTab("Граф", panelGraphTab);
        add(tabbedPane, BorderLayout.CENTER);

        JSplitPane splitPaneGraphTab1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelGraphTab.add(splitPaneGraphTab1, BorderLayout.CENTER);

        // Левая часть: ввод, кнопки, тип графа
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        textAreaGraphFile = new JTextArea(12, 30);
        textAreaGraphFile.setText("5 6\n0 1\n1 2\n2 3\n3 0\n1 3\n0 4\n");
        leftPanel.add(new JScrollPane(textAreaGraphFile));

        JPanel filePanel = new JPanel();
        buttonLoadGraphFromFile = new JButton("Загрузить из файла");
        buttonSaveGraphToFile = new JButton("Сохранить в файл");
        filePanel.add(buttonLoadGraphFromFile);
        filePanel.add(buttonSaveGraphToFile);
        leftPanel.add(filePanel);

        JPanel typePanel = new JPanel();
        comboBoxGraphType = new JComboBox<>(new String[] {
            "Н-граф (AdjMatrixGraph)",
            "Н-граф (AdjListsGraph)"
        });
        typePanel.add(comboBoxGraphType);
        buttonCreateGraph = new JButton("Построить граф");
        typePanel.add(buttonCreateGraph);
        leftPanel.add(typePanel);

        // Блок обходов и поиска цикла
        JPanel searchPanel = new JPanel();
        buttonFindShortestCycle = new JButton("Найти длину кратчайшего цикла");
        searchPanel.add(buttonFindShortestCycle);
        leftPanel.add(searchPanel);

        splitPaneGraphTab1.setLeftComponent(leftPanel);

        // Правая часть: визуализация и вывод
        JPanel rightPanel = new JPanel(new BorderLayout());
        panelGraphPainterContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Можно добавить простую визуализацию, если нужно
            }
        };
        panelGraphPainterContainer.setPreferredSize(new Dimension(400, 300));
        rightPanel.add(panelGraphPainterContainer, BorderLayout.CENTER);

        textAreaSystemOut = new JTextArea(6, 30);
        textAreaSystemOut.setEditable(false);
        rightPanel.add(new JScrollPane(textAreaSystemOut), BorderLayout.SOUTH);

        splitPaneGraphTab1.setRightComponent(rightPanel);
        splitPaneGraphTab1.setDividerLocation(350);

        // === Обработчики ===
        buttonLoadGraphFromFile.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try (Scanner sc = new Scanner(fc.getSelectedFile())) {
                    sc.useDelimiter("\\Z");
                    textAreaGraphFile.setText(sc.next());
                } catch (Exception ex) {
                    showSystemOut("Ошибка загрузки файла: " + ex.getMessage());
                }
            }
        });
        buttonSaveGraphToFile.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try (FileWriter wr = new FileWriter(fc.getSelectedFile())) {
                    wr.write(textAreaGraphFile.getText());
                } catch (Exception ex) {
                    showSystemOut("Ошибка сохранения файла: " + ex.getMessage());
                }
            }
        });
        buttonCreateGraph.addActionListener(e -> {
            try {
                String selectedGraph = (String) comboBoxGraphType.getSelectedItem();
                Class clz = "Н-граф (AdjMatrixGraph)".equals(selectedGraph)
                        ? AdjMatrixGraph.class
                        : AdjMatrixGraph.class; // AdjListsGraph.class
                graph = GraphUtils.fromStr(textAreaGraphFile.getText(), clz);
                showSystemOut("Граф построен. Вершин: " + graph.vertexCount() + ", рёбер: " + graph.edgeCount());
                panelGraphPainterContainer.repaint();
            } catch (Exception ex) {
                showSystemOut("Ошибка построения графа: " + ex.getMessage());
            }
        });
        buttonFindShortestCycle.addActionListener(e -> {
            if (graph == null) {
                showSystemOut("Сначала постройте граф!");
                return;
            }
            int cycleLen = GraphAlgorithms.shortestCycleLength(graph);
            if (cycleLen == -1) {
                showSystemOut("В графе нет циклов.");
            } else {
                showSystemOut("Длина кратчайшего цикла: " + cycleLen);
            }
        });
    }

    private void showSystemOut(String msg) {
        textAreaSystemOut.setText(msg);
    }
} 