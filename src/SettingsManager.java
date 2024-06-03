import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс для работы с xml файлом. Загружает или считывает настройки
 */
public class SettingsManager {

  // количество потоков для записи
  private int amountWriteThreads;
  // количество потоков для чтения
  private int amountReadThreads;
  // скорость записи
  private int writeSpeed;
  // скорость чтения
  private int readSpeed;
  // размер буфера
  private int bufferSize;

  /**
   * Метод для установки количества потоков для записи
   *
   * @param amountWriteThreads принимает количество потоков
   */
  public void setAmountWriteThreads(int amountWriteThreads) {
    // если количество потоков меньше 1, то устанавливается стандартное значение
    if (amountWriteThreads > 0) {
      this.amountWriteThreads = amountWriteThreads;
    } else {
      this.amountReadThreads = 4;
      System.out.println("Количество потоков должно быть больше 0");
    }
  }

  /**
   * Метод для установки количество потоков для записи
   *
   * @param amountReadThreads принимает количество потоков для чтения
   */
  public void setAmountReadThreads(int amountReadThreads) {
    // если количество потоков меньше 1, то устанавливаются стандартные значения
    if (amountReadThreads > 0) {
      this.amountReadThreads = amountReadThreads;
    } else {
      this.amountReadThreads = 4;
      System.out.println("Количество потоков должно быть больше 0");
    }

  }

  public int getAmountWriteThreads() {
    return amountWriteThreads;
  }

  public int getAmountReadThreads() {
    return amountReadThreads;
  }

  public int getWriteSpeed() {
    return writeSpeed;
  }

  public int getReadSpeed() {
    return readSpeed;
  }

  public int getBufferSize() {
    return bufferSize;
  }

  /**
   * Метод для установки скорости записи
   *
   * @param writeSpeed
   */
  public void setWriteSpeed(int writeSpeed) {
    if (writeSpeed >= 0) {
      this.writeSpeed = writeSpeed;
    } else {
      this.writeSpeed = 1000;
      System.out.println("Длительность процесса записи должна быть больше или равно 1000");
    }
  }

  /**
   * Метод для установки скорости чтения
   *
   * @param readSpeed
   */
  public void setReadSpeed(int readSpeed) {
    if (readSpeed >= 0) {
      this.readSpeed = readSpeed;
    } else {
      this.readSpeed = 1000;
      System.out.println("Длительность процесса чтения должна быть больше или равно 1000");
    }

  }

  /**
   * Метод для установки размера буферного пула
   *
   * @param bufferSize
   */
  public void setBufferSize(int bufferSize) {
    if (bufferSize > 0) {
      this.bufferSize = bufferSize;
    } else {
      this.bufferSize = 2;
      System.out.println("Размер буфера должен быть больше 0");
    }
  }

  /**
   * Метод для загрузки данных с файла загрузки
   */
  public void setUpSettings() {
    // инициализация файла
    File file = new File("src\\settings.xml");
    if (file.exists()) {
      try {

        NodeList nodeList;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        Element root = doc.getDocumentElement();

        // считывание данных
        nodeList = root.getElementsByTagName("amountWriteThreads");
        setAmountWriteThreads(Integer.parseInt(nodeList.item(0).getTextContent()));
        nodeList = root.getElementsByTagName("amountReadThreads");
        setAmountReadThreads(Integer.parseInt(nodeList.item(0).getTextContent()));
        nodeList = root.getElementsByTagName("writeSpeed");
        setWriteSpeed(Integer.parseInt(nodeList.item(0).getTextContent()));
        nodeList = root.getElementsByTagName("readSpeed");
        setReadSpeed(Integer.parseInt(nodeList.item(0).getTextContent()));
        nodeList = root.getElementsByTagName("bufferSize");
        setBufferSize(Integer.parseInt(nodeList.item(0).getTextContent()));

      } catch (Exception e) {
        e.printStackTrace();
        setAmountWriteThreads(4);
        setAmountReadThreads(4);
        setWriteSpeed(1000);
        setReadSpeed(1000);
        setBufferSize(2);
        System.out.println("Файл неисправен. Были установлены стандартные значения.");
      }
    } else {
      setAmountWriteThreads(4);
      setAmountReadThreads(4);
      setWriteSpeed(1000);
      setReadSpeed(1000);
      setBufferSize(2);
      System.out.println("Файл не найден. Были установлены стандартные значения.");
    }
  }

  /**
   * Метод для обновления файла настроек
   */
  public void updateSettings() {
    try {
      String string =
          "<settings>\n"
              + "\t<amountWriteThreads>" + getAmountWriteThreads() + "</amountWriteThreads>\n"
              + "\t<amountReadThreads>" + getAmountReadThreads() + "</amountReadThreads>\n"
              + "\t<writeSpeed>" + getWriteSpeed() + "</writeSpeed>\n"
              + "\t<readSpeed>" + getReadSpeed() + "</readSpeed>\n"
              + "\t<bufferSize>" + getBufferSize() + "</bufferSize>\n"
              + "</settings>";
      FileWriter writer = new FileWriter("src\\settings.xml");
      writer.write(string);
      writer.close();
      System.out.println("Настройки обновлены...");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

}
