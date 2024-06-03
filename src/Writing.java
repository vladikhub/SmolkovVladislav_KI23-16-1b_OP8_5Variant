import java.util.Random;

/**
 * Класс-поток для записи информации в пул
 */
public class Writing extends Thread {

  // Скорость записи
  public int speed;
  // Буферный пул
  private Buffer pool;

  /**
   * Конструктор класса
   *
   * @param pool
   * @param speed принимает буферный пул и скорость записи
   */
  public Writing(Buffer pool, int speed) {
    this.pool = pool;
    this.speed = speed;
  }

  /**
   * Метод run() при запуске потока. Берет пустую ячейку и записывает туда информацию
   */
  public void run() {
    try {
      // Проверка, имеются ли ячейки для записи
      while (pool.getAmountWritePools() > 0) {
        // генерация случайного число и запись его в ячейку
        Random random = new Random();
        String answer = generateAnswer(random.nextInt(10));
        System.out.println(currentThread().getName() + " записал значение: " + answer);
        pool.writePool(answer);
        pool.setAmountWriteThreads(pool.getAmountWritePools() - 1);
        pool.setAmountReadThreads(pool.getAmountReadPools() + 1);
        Thread.sleep(speed);
      }
      System.out.println("Все ячейки заняты, пожалуйста, подождите...");
    } catch (InterruptedException ex) {
      System.out.println(currentThread().getName() + " прерван.");
    }
  }

  /**
   * Метод для генерации случайных чисел
   *
   * @param length принимает размер строки
   * @return возвращает строку
   */
  public static String generateAnswer(int length) {
    String chars = "0123456789";
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      int randomIndex = random.nextInt(chars.length());
      char randomChar = chars.charAt(randomIndex);
      sb.append(randomChar);
    }
    return sb.toString();
  }
}
