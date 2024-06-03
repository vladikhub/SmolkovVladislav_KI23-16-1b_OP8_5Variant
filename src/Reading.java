/**
 * Класс-поток для чтения записей пула.
 */
public class Reading extends Thread{
  // Скорость чтения
  public int speed;
  // Буферный пул
  private Buffer pool;

  /**
   * Конуструктор класса
   * @param pool
   * @param speed
   * принимает буферный пул и скорость чтения
   */
  public Reading(Buffer pool, int speed){
    this.pool = pool;
    this.speed = speed;
  }


  /**
   * Метод run() при запуске потока. Берет записанную ячейку и считывает информациию
   */
  public void run(){
    try {
      // проверка, есть ли свободная ячейка
      while (pool.getAmountReadPools() > 0){
        // считываем информацию с записанной
        Object data = pool.readPool();
        // если ячейка не пуста, то выводим результат. Уменьшаем количество ячеек записанных. Увеличиваем количество ячеек пустых.
        if (data != null){
          System.out.println(currentThread().getName() + " считал ячейку: " + data);
          pool.setAmountReadThreads(pool.getAmountReadPools() - 1);
          pool.setAmountWriteThreads(pool.getAmountWritePools() + 1);
        }
        sleep(speed);
      }
      System.out.println("Все ячейки пусты, подождите");
      sleep(speed);
    } catch(InterruptedException ex){
      System.out.println(currentThread().getName() + " прерван.");
    }
  }

}
