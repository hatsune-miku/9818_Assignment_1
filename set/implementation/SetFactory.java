package set.implementation;

import set.SetFactoryI;
import set.SetI;

// import java.util.Random;

public class SetFactory implements SetFactoryI {
    // private Random random = new Random(System.currentTimeMillis());

    private SetI set(int p1, int p2) {
        return new Set(p1, p2);

        /*~
       /// Used to test my set and friends'

        if (random.nextBoolean()) {
            System.out.println("Set from gz");
            return new Set(p1, p2);
        }
        System.out.println("Set from lsjy");
        return new MySet(p1, p2);

         */
    }

    @Override
    public SetI makeSet() {
        return set(0, 0);
    }

    @Override
    public SetI makeSet(int x) {
        return set(x, 1);
    }

    @Override
    public SetI makeSet(int first, int count) {
        return set(first, count);
    }
}
