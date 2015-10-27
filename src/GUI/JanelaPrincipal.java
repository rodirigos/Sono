package GUI;


import java.awt.Dimension;
import javax.swing.*;



   public class JanelaPrincipal extends JFrame    
   {
     //GUI COMPONENTS
     private JTabbedPane tabs;
     private InputPanel inputPanel;

    public JanelaPrincipal()
    {       
            startComponents();
    }
    
    /**
     * Inicia os componentes de interface gráfica
     */
    public void startComponents(){
        //CREATE AND SET COMPONENTS
        tabs= new JTabbedPane();
        inputPanel= new InputPanel();

        //ADD COMPONENTS TO THE MAIN CONTENT PANEL
        
        tabs.add("Input",inputPanel);
        //SET UP FRAME
     
        this.setTitle("SONO");
        this.setContentPane(tabs);
        this.setSize(new Dimension(800,600));
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }   
    
   }
