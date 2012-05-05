/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmark.base;

/**
 *
 * @author rishin.goswami
 */
public interface Runner {

    void NotifyResult(String name, Object result);

    void NotifyError(String name, Exception error);

    void NotifyScore(int score);

    void NotifyStart(String name);
}
