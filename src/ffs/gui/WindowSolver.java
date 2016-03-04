/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffs.gui;

import ffs.algorithm.FlowFree;
import ffs.algorithm.Search;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Main frame of the Flow Free Solver
 * @author Cristina Barreno, Victor Rodriguez
 */
public final class WindowSolver extends JFrame{
    
    private GridUI grid;
    
    private JMenuBar bar;
    
    private JMenu menu_file;
    private JButton create;
    private JMenuItem m_exit;
    private JButton solve;
    
    private JMenu menu_edition;
    private JButton change_color;
    
    private JSlider slider;
    private JScrollPane sp;
    private JFrame frame;
    private JColorChooser color_chooser;
    
    private JPanel wc;
    
    public WindowSolver(){
        frame = this;
        this.setTitle("Solver Flow Free");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(1024, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        initComponent();
        actionComponents();
        addComponents();
        
        this.revalidate();
        this.setVisible(true);
        
    }
    
    public void initComponent(){
        
        // Grid initialize
        grid = new GridUI();
        sp = new JScrollPane(grid);
        wc = new JPanel();
        
        // Second bar initialize
        create = new JButton("Create");
        solve = new JButton("Solve");
        solve.setEnabled(false);
        
        // Bar menu initialize
        bar = new JMenuBar();
        
        // Menu File
        menu_file = new JMenu("File");
        m_exit = new JMenuItem("Exit");
        
        
        // Menu Edition
        menu_edition = new JMenu("Edit");
        
        slider = sliderInitialize();
        
        // Color chooser
        color_chooser = colorChooserInitialize();
        color_chooser.setPreferredSize(new Dimension(430,600));
        
        change_color = new JButton("Change color");
        
        //Change the style interface
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
    }
    /**
     * Added to each of the graphics components to the window
     */
    public void addComponents(){
        // First bar
        bar.add(menu_file);
        bar.add(menu_edition);
        
        //Second bar
        wc.add(create);
        wc.add(solve);  
        
        // File menu
        menu_file.add(m_exit);   
        
        bar.add(wc, BorderLayout.SOUTH);
        color_chooser.add(slider, BorderLayout.SOUTH);
        this.add(bar, BorderLayout.NORTH);
        this.add(sp, BorderLayout.CENTER);
        //this.add(slider, BorderLayout.SOUTH);
        this.add(color_chooser, BorderLayout.EAST);
    }
    
    /**
     * Add to each of the graphics components, events to be performed 
     * on the window.
     */
    public void actionComponents(){
        
        m_exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        
        slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                int n;
                JSlider s = (JSlider) ce.getSource();
                if (!s.getValueIsAdjusting()){
                    n = (int)s.getValue();
                    grid.zoomChange(n);
                    grid.setPreferredSize(
                            new Dimension(CellUI.dim * grid.getN(), 
                                    CellUI.dim * grid.getN()));
                    grid.validate();
                    grid.repaint();
                    sp.setViewportView(grid);
                }
            }
        });
        
        color_chooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                grid.setActualColor(color_chooser.getColor());
            }
        });
        
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = CreateInitialize();
                solve.setEnabled(true);
                slider.setEnabled(true);
                grid = new GridUI(n, n);
                sp.setViewportView(grid);
                sp.repaint();
            }
        });
        
        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(!grid.isGrid_correct())
                    JOptionPane.showMessageDialog(rootPane, 
                            "Not dot points are in group 2",
                            "Grid Error",
                            JOptionPane.ERROR_MESSAGE);
                else{
                    
                    FlowFree ff = new FlowFree(grid.toArrayChar(), null);
                    Search s = new Search(ff, grid);
                    Thread thread = new Thread(s);
                    s.run();
                }
            }
        });
    } 
    
    /**
     * Configure the size of the cells in the grid. The possibles values that
     * can have are between 25 to 100 px.
     * @return JSlider with the min value 25, max value 100, initial value 
     * equal to width cell.
     */
    public JSlider sliderInitialize(){
        JSlider s = new JSlider();
        s.setEnabled(false);
        s.setValue(CellUI.dim);
        s.setMinimum(25);
        s.setMaximum(100);
        s.setMinorTickSpacing(25);
        s.setMajorTickSpacing(100);
        s.setPaintTicks(true);
        s.setPaintTrack(true);
        s.setPaintLabels(true);
        s.setFont(new Font("Arial", Font.BOLD, 15));
        return s;
    }
    
    /**
     * Create a JSpinner to request the dimensiones of the grid. Remember that
     * grid is square. <br/>
     * In adittion, the maximum value and the minimun value you can enter
     * is the between 2 - 15. Because of the computational cost.
     * @return JSpinner with min value 2 and max value 15.
     */
    public int CreateInitialize(){
        SpinnerNumberModel sModel = new SpinnerNumberModel(2, 2, 15, 1);
        JSpinner spinner = new JSpinner(sModel);
        JOptionPane.showOptionDialog(null, spinner, 
                "Enter the dimension grid", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        return (int) spinner.getValue();
    }
    
    /**
     * Initialize the JColorChooser without the tabs HSB, RGB and CMYK.
     * Also remove recent and preview color
     * @return JColorChooser without the elements before commented
     */
    private JColorChooser colorChooserInitialize(){
        JColorChooser cc = new JColorChooser();
        AbstractColorChooserPanel[] panels = cc.getChooserPanels();
        
        for (int i = 1; i < panels.length; i++) {
            cc.removeChooserPanel(panels[i]);
        }
        
        JPanel p = (JPanel) cc.getChooserPanels()[0].getComponent(0);
        p.remove(2);
        p.remove(1);
        cc.setPreviewPanel(new JPanel());
        return cc;
    }
}