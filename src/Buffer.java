/**
 * Класс для хранения ячеек данных. Он имеет методы для записи и чтения данных из буфера.
 */
public class Buffer {

  // Массив ячеек
  private Object[] pool;

  // Размер пула
  private int poolSize;

  // Количество ячеек для чтения
  private int amountReadPools;

  // Количество ячеек для записи
  private int amountWritePools;

  /**
   * Конструктор буфера. Задает количество ячеек для чтения и записи.
   *
   * @param poolSize принимает размер пула
   */
  public Buffer(int poolSize) {
    this.poolSize = poolSize;
    pool = new Object[poolSize];
    setAmountReadThreads(0);
    setAmountWriteThreads(poolSize);
  }

  /**
   * Синхронизированный метод для записи в пул
   *
   * @param data принимает данные для записи
   */
  public synchronized void writePool(Object data) {
    pool[getPoolSize() % amountWritePools] = data;
  }

  /**
   * Синхронизированный метод для чтения данных из пула. Если ячейку прочитали, заменяем ее на null
   *
   * @return данные или null
   */
  public synchronized Object readPool() {
    for (int i = 0; i < getPoolSize(); i++) {
      if (pool[i] != null) {
        Object data = pool[i];
        pool[i] = null;
        return data;
      }
    }
    return null;
  }

  /**
   * Метод задает буферный пул
   *
   * @param pool
   */
  public void setPool(Object[] pool) {
    this.pool = pool;
  }

  /**
   * Метод задает размер пула
   *
   * @param poolSize
   */
  public void setPoolSize(int poolSize) {
    this.poolSize = poolSize;
  }

  /**
   * Метод задает количество ячеек для чтения
   *
   * @param amountReadPools
   */
  public void setAmountReadThreads(int amountReadPools) {
    this.amountReadPools = amountReadPools;
  }

  /**
   * Метод задает количество ячеек для записи
   *
   * @param amountWritePools
   */
  public void setAmountWriteThreads(int amountWritePools) {
    this.amountWritePools = amountWritePools;
  }

  /**
   * Возвращает буферный пул
   *
   * @return
   */
  public Object[] getPool() {
    return pool;
  }

  /**
   * Возвращает количество ячеек для чтения
   *
   * @return
   */
  public int getAmountReadPools() {
    return amountReadPools;
  }

  /**
   * Возвращает количество ячеек для записи
   *
   * @return
   */
  public int getAmountWritePools() {
    return amountWritePools;
  }

  /**
   * Возвраащет размер пула
   *
   * @return
   */
  public int getPoolSize() {
    return poolSize;
  }


}
