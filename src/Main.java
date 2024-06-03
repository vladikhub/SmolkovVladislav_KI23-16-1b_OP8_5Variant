import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Главный класс Меню
 */
public class Main {

  // количество потоков записи
  private static int amountWriteThreads;

  // количество потоков чтения
  private static int amountReadThreads;

  // скорость записи
  private static int writeSpeed;

  // скорость чтения
  private static int readSpeed;

  // размер буфера
  private static int bufferSize;
  public static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    // инициализация класса считывания настроек
    SettingsManager manager = new SettingsManager();

    System.out.println("""
        ___МЕНЮ___
        Выберете пункт:
        1 - загрузить данные с файла и запустить
        2 - ввести данные вручную и запустить""");
    int num = 0;
    boolean correct = false;
    while (!correct) {
      try {
        num = sc.nextInt();
        correct = true;
      } catch (InputMismatchException ex) {
        System.out.println("Введите число (0/1):");
      }
    }
    switch (num) {
      case 1:
        // загрузка данных с файла
        manager.setUpSettings();
        break;
      case 2:
        // ввод данных вручную
        inputData();
        manager.setAmountWriteThreads(amountWriteThreads);
        manager.setAmountReadThreads(amountReadThreads);
        manager.setWriteSpeed(writeSpeed);
        manager.setReadSpeed(readSpeed);
        manager.setBufferSize(bufferSize);

        // обновление данных в файле
        manager.updateSettings();
        // загрузка с файла
        manager.setUpSettings();
        break;
      default:
        System.out.println("Число должно быть от 1 до 2");
    }
    // инициализация буферного пула
    Buffer pool = new Buffer(manager.getBufferSize());

    // создание новых потоков для записи и запуск их
    for (int i = 0; i < manager.getAmountWriteThreads(); i++) {
      Writing writing = new Writing(pool, manager.getWriteSpeed());
      writing.setName("Writing_" + (i + 1));
      writing.start();
    }

    // создание новых потоков для чтения и запуск их
    for (int i = 0; i < manager.getAmountReadThreads(); i++) {
      Reading reading = new Reading(pool, manager.getReadSpeed());
      reading.setName("Reading_" + (i + 1));
      reading.start();
      // задержка после создания потока, чтобы потоки записи успели записать информацию
      try {
        Thread.sleep(500);
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Метод для ввода данных вручную
   */
  public static void inputData() {
    System.out.println("Введите количество поток для записи: ");
    try {
      amountWriteThreads = sc.nextInt();
      if (amountWriteThreads < 1) {
        throw new InputMismatchException("Введено число меньше 1...");
      }
    } catch (InputMismatchException ex) {
      System.out.println("Неправильный ввод. Введите число еще раз ( >= 0 и <= 2 147 483 647):");
      amountWriteThreads = sc.nextInt();
    }
    sc.nextLine();

    System.out.println("Введите количество поток для чтения: ");
    try {
      amountReadThreads = sc.nextInt();
      if (amountReadThreads < 1) {
        throw new InputMismatchException("Введено число меньше 1...");
      }
    } catch (InputMismatchException ex) {
      System.out.println("Неправильный ввод. Введите число еще раз ( >= 0 и <= 2 147 483 647):");
      amountReadThreads = sc.nextInt();
    }
    sc.nextLine();

    System.out.println("Введите длительность операции записи: ");
    try {
      writeSpeed = sc.nextInt();
      if (writeSpeed < 1000) {
        throw new InputMismatchException("Введено число меньше 1000...");
      }
    } catch (InputMismatchException ex) {
      System.out.println("Неправильный ввод. Введите число еще раз ( >= 1000 и <= 2 147 483 647):");
      writeSpeed = sc.nextInt();
    }
    sc.nextLine();

    System.out.println("Введите длительность операции чтения: ");
    try {
      readSpeed = sc.nextInt();
      if (readSpeed < 1000) {
        throw new InputMismatchException("Введено число меньше 1000...");
      }
    } catch (InputMismatchException ex) {
      System.out.println("Неправильный ввод. Введите число еще раз ( >= 1000 и <= 2 147 483 647):");
      readSpeed = sc.nextInt();
    }
    sc.nextLine();

    System.out.println("Введите количество ячеек в буфере: ");
    try {
      bufferSize = sc.nextInt();
      if (bufferSize < 1) {
        throw new InputMismatchException("Введено число меньше 1...");
      }
    } catch (InputMismatchException ex) {
      System.out.println("Неправильный ввод. Введите число еще раз ( > 0 и <= 2 147 483 647):");
      bufferSize = sc.nextInt();
    }
    sc.nextLine();
  }
}