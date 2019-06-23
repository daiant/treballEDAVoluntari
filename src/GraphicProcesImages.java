package src;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.io.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.imageio.ImageIO;

public class GraphicProcesImages extends JPanel  implements ActionListener {
    //atributos
    static String originalString  = "Original";
    static String llindarString  = "Llindar";
    private String imageFileName = null;
    private JLabel picture;
    private int value=0;
    private int[][] img = null;

    //constructor
    public GraphicProcesImages() {
        super(new BorderLayout());

        //creación de botones.
	JRadioButton originalButton = new JRadioButton(originalString);
        originalButton.setMnemonic(KeyEvent.VK_A);
        originalButton.setActionCommand(originalString);
        originalButton.setSelected(true);

        JRadioButton llindarButton = new JRadioButton(llindarString);
        llindarButton.setMnemonic(KeyEvent.VK_L);
        llindarButton.setActionCommand(llindarString);


        //agrupar botones.
        ButtonGroup group = new ButtonGroup();
	group.add(originalButton);
	group.add(llindarButton);

        //activar el manejador de senyales para los botones.
	originalButton.addActionListener(this);
	llindarButton.addActionListener(this);

	picture = new JLabel();
        picture.setPreferredSize(new Dimension(450, 550));


        //poner los botones en el panel principal
        JPanel radioPanel = new JPanel(new GridLayout(0,1));
	radioPanel.add(originalButton);
	radioPanel.add(llindarButton);

	//anyadir boton cargar imagen
	JButton openButton = new JButton("Cargar imagen...");
	openButton.setActionCommand("openFile");
        openButton.addActionListener(this);


	//anyadir los bloques a la pantalla principal
        add(radioPanel, BorderLayout.LINE_START);
        add(picture, BorderLayout.CENTER);
	add(openButton, BorderLayout.PAGE_END);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

	//manejador de senyales para cuando se modifique el tamanyo de la ventana
	addComponentListener(new ComponentListener(){
		public void componentHidden(ComponentEvent evt){}
		public void componentShown(ComponentEvent evt) {}
		public void componentMoved(ComponentEvent evt) {}
		public void componentResized(ComponentEvent evt) {
		    Component c = (Component)evt.getSource();

		    Dimension newSize = c.getSize();
		    BufferedImage image;

		    if (img != null)
			image =matrizToImagen(img);
		    else
			image = new BufferedImage(450, 550,java.awt.Image.SCALE_SMOOTH );

		    ImageIcon imageIcon = new ImageIcon(image);
		    Image image_aux = imageIcon.getImage();
		    Image image_aux2 = image_aux.getScaledInstance(newSize.width-150, (int)(newSize.height*(image.getHeight() /(double)image.getWidth())),  java.awt.Image.SCALE_SMOOTH);
		    imageIcon = new ImageIcon(image_aux2);
		    picture.setIcon(imageIcon);
		}
	    });

    }

    private int elegirValor(int ini, int fi, int orig){
	JOptionPane optionPane = new JOptionPane();
	JSlider slider = new JSlider(JSlider.HORIZONTAL,ini,fi,orig);
	slider.setMajorTickSpacing(100);
	slider.setMinorTickSpacing(10);
	slider.setPaintTicks(true);
	slider.setPaintLabels(true);
	slider.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent evt) {
		    JSlider slider = (JSlider) evt.getSource();
		    if (!slider.getValueIsAdjusting())
			value = slider.getValue();
		}
	    });


	optionPane.setMessage(new Object[] { "Select a value: ", slider });
	optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
	optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
	JDialog dialog = optionPane.createDialog(this, "Choose a level");
	dialog.setVisible(true);
	return value;

    }

    public void actionPerformed(ActionEvent e) {

	try{
	    switch (e.getActionCommand()) {

	    case "openFile":
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    imageFileName = fc.getSelectedFile().getAbsolutePath();

		}
		img = cargarImagenDeFichero(imageFileName);
		break;
	    case "Original":
		img = cargarImagenDeFichero(imageFileName);
		break;
	    case "Llindar":
                value=elegirValor(0,200,0);

                ProcessatImages proc = new ProcessatImages(img,value);
		proc.netejar();
		img = proc.getImage();
                break;
	    }


	    BufferedImage image = matrizToImagen(img);
	    ImageIcon imageIcon = new ImageIcon(image);

	    Image image_aux = imageIcon.getImage();
	    Dimension size = this.getSize();
	    Image image_aux2 = image_aux.getScaledInstance(size.width-150, (int)(size.height*(image.getHeight() /(double)image.getWidth())),  java.awt.Image.SCALE_SMOOTH);
	    imageIcon = new ImageIcon(image_aux2);

	    picture.setIcon(imageIcon);


	}catch(Exception ex){
	    JOptionPane.showMessageDialog(null, "Image not uploaded", "Error ", JOptionPane.INFORMATION_MESSAGE);

	}
    }

    private static void  createAndShowGUI() {
        JFrame frame = new JFrame("Práctica 5: Matrices");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new GraphicProcesImages();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
	frame.pack();
	frame.setVisible(true);
    }


    /** Convierte una matriz de enteros entre 0 y 255 a una BufferedImage
     */
    public static BufferedImage matrizToImagen(int[][] m) {
        int ancho = m[0].length;
        int alto = m.length;
        BufferedImage image = new BufferedImage(ancho, alto, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                int gray = m[i][j];
                Color c = new Color(gray, gray, gray);
                image.setRGB(j, i, c.getRGB());
            }
        }
        return image;
    }


    /* Convierte la imagen inputImage a escala de grises
     **/
    private static BufferedImage getGrayScale(BufferedImage inputImage) {
        BufferedImage img = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
                                              BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = img.getGraphics();
        g.drawImage(inputImage, 0, 0, null);
        g.dispose();
        return img;
    }


    /*
     * Lee del fichero fileName una imagen, la convierte a escala de grises y la
     * almacena en una matriz de enteros entre 0 y 255; 0 es el negro y 255 el blanco
     * @param String fileName, nombre del fichero donde se encuentra la imagen
     * @return int[][], matriz de valores entre 0 y 255 que representa la imagen original en blanco y negro
    **/
    public  int[][] cargarImagenDeFichero(String fileName) {
        int[][] img;
        try {
            BufferedImage imageRGB = ImageIO.read(new File(fileName));
            BufferedImage image = getGrayScale(imageRGB);
            int alto = image.getHeight();
            int ancho = image.getWidth();
            img = new int[alto][ancho];

            for (int i = 0; i < alto; i++) {
                for (int j = 0; j < ancho; j++) {
                    int pix = image.getRGB(j, i);
                    Color c = new Color(pix);
                    int r = c.getRed(), g = c.getGreen(), b = c.getBlue();
                    img[i][j] = (r + g + b) / 3;
                }
            }
            return img;
        }
        catch (IOException e) { return null; }
    }



    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
