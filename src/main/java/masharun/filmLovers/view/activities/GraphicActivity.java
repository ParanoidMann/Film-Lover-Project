package masharun.filmLovers.view.activities;

import masharun.filmLovers.Main;
import masharun.filmLovers.model.DAO.ScoreDAO;
import masharun.filmLovers.model.entities.ScoreCount;
import masharun.filmLovers.view.Config;
import masharun.filmLovers.view.OptionPane;

import javax.swing.*;
import java.sql.SQLException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import static masharun.filmLovers.view.Config.TITLES.GRAPHIC_FORM_TITLE;
import static masharun.filmLovers.view.Config.TITLES.MAIN_TITLE;

public class GraphicActivity implements Activity {
    private JPanel rootPanel;
    private JPanel headerPanel;
    private JPanel footerPanel;
    private JButton exitButton;
    private JButton backButton;
    private JLabel mainTitle;
    private JLabel userNameTitle;
    private JPanel graphicPanel;
    
    public GraphicActivity() {
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( GRAPHIC_FORM_TITLE );
        userNameTitle.setText( Main.getCurrentUser().getName() +
                " " + Main.getCurrentUser().getSurname() + ", " + Main.getCurrentUser().getBirthday() );
        init();
        initListeners();
    }
    
    private void init() {
        try {
            JFreeChart chart = ChartFactory.createPieChart(
                    "Количество оценок", createDataset(), true, true, false );
            rootPanel = new ChartPanel( chart );
            rootPanel.add( exitButton );
            rootPanel.add( backButton );
            
        } catch( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки графика", "Ошибка" );
            System.err.println( e.toString() );
        }
    }
    
    private PieDataset createDataset() throws SQLException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for( ScoreCount value : new ScoreDAO().selectCountScore( Main.getCurrentUser().getLogin() ) ) {
            dataset.setValue( value.getScore(), value.getCount() );
        }
        return dataset;
    }
    
    private void initListeners() {
        backButton.addActionListener( eventListeners -> Main.getMainActivity().setLastForm() );
        
        exitButton.addActionListener( eventListeners -> {
            Main.setCurrentUser( null );
            Main.getMainActivity().clearStackFrame();
            Main.getMainActivity().setNewForm( new MainActivity() );
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Config.TITLES.setTitle( GRAPHIC_FORM_TITLE );
    }
}