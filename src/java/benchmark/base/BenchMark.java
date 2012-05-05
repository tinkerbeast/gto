/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmark.base;

/**
 *
 * @author rishin.goswami
 */
public interface BenchMark {

    String getName();

    void Run() throws Exception;

    void Setup();

    void TearDown() throws Exception;
}
