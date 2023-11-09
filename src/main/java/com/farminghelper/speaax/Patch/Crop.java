package com.farminghelper.speaax.Patch;

import java.util.stream.IntStream;

public enum Crop
{
    // ----- ----- ----- ----- ----- ----- -----
    // Fruit Trees

    APPLE(
        IntStream.range(  8, 13), // growing     - tree[Inspect,Guide]
        IntStream.range( 14, 20), // harvestable - tree[Inspect,Guide,Chop-down,Pick-{fruit}]
        IntStream.range( 21, 26), // diseased    - tree[Inspect,Guide,Prune]
        IntStream.range( 27, 32), // dead        - tree[Inspect,Guide,Clear]
        IntStream.range( 33, 33), // remove      - tree[Inspect,Guide,Clear] (stump)
        IntStream.range( 34, 34)  // grown       - tree[Inspect,Guide,Check-health]
    ),
    BANANA(
        IntStream.range( 35, 40), // growing     - tree[Inspect,Guide]
        IntStream.range( 41, 47), // harvestable - tree[Inspect,Guide,Chop-down,Pick-{fruit}]
        IntStream.range( 48, 53), // diseased    - tree[Inspect,Guide,Prune]
        IntStream.range( 54, 59), // dead        - tree[Inspect,Guide,Clear]
        IntStream.range( 60, 60), // remove      - tree[Inspect,Guide,Clear] (stump)
        IntStream.range( 61, 61)  // grown       - tree[Inspect,Guide,Check-health]
    ),
    ORANGE(
        IntStream.range( 72, 77), // growing     - tree[Inspect,Guide]
        IntStream.range( 78, 84), // harvestable - tree[Inspect,Guide,Chop-down,Pick-{fruit}]
        IntStream.range( 85, 90), // diseased    - tree[Inspect,Guide,Prune]
        IntStream.range( 91, 96), // dead        - tree[Inspect,Guide,Clear]
        IntStream.range( 97, 97), // remove      - tree[Inspect,Guide,Clear] (stump)
        IntStream.range( 98, 98)  // grown       - tree[Inspect,Guide,Check-health]
    ),
    CURRY(
        IntStream.range( 99,104), // growing     - tree[Inspect,Guide]
        IntStream.range(105,111), // harvestable - tree[Inspect,Guide,Chop-down,Pick-{fruit}]
        IntStream.range(112,117), // diseased    - tree[Inspect,Guide,Prune]
        IntStream.range(118,123), // dead        - tree[Inspect,Guide,Clear]
        IntStream.range(124,124), // remove      - tree[Inspect,Guide,Clear] (stump)
        IntStream.range(125,125)  // grown       - tree[Inspect,Guide,Check-health]
    ),
    PINEAPPLE(
        IntStream.range(136,141), // growing     - tree[Inspect,Guide]
        IntStream.range(142,148), // harvestable - tree[Inspect,Guide,Chop-down,Pick-{fruit}]
        IntStream.range(149,154), // diseased    - tree[Inspect,Guide,Prune]
        IntStream.range(155,160), // dead        - tree[Inspect,Guide,Clear]
        IntStream.range(161,161), // remove      - tree[Inspect,Guide,Clear] (stump)
        IntStream.range(162,162)  // grown       - tree[Inspect,Guide,Check-health]
    ),
    PAPAYA(
        IntStream.range(163,168), // growing     - tree[Inspect,Guide]
        IntStream.range(169,175), // harvestable - tree[Inspect,Guide,Chop-down,Pick-{fruit}]
        IntStream.range(176,181), // diseased    - tree[Inspect,Guide,Prune]
        IntStream.range(182,187), // dead        - tree[Inspect,Guide,Clear]
        IntStream.range(188,188), // remove      - tree[Inspect,Guide,Clear] (stump)
        IntStream.range(189,189)  // grown       - tree[Inspect,Guide,Check-health]
    ),
    PALM(
        IntStream.range(200,205), // growing     - tree[Inspect,Guide]
        IntStream.range(206,212), // harvestable - tree[Inspect,Guide,Chop-down,Pick-{fruit}]
        IntStream.range(213,218), // diseased    - tree[Inspect,Guide,Prune]
        IntStream.range(219,224), // dead        - tree[Inspect,Guide,Clear]
        IntStream.range(225,225), // remove      - tree[Inspect,Guide,Clear] (stump)
        IntStream.range(226,226)  // grown       - tree[Inspect,Guide,Check-health]
    ),
    DRAGONFRUIT(
        IntStream.range(227,232), // growing     - tree[Inspect,Guide]
        IntStream.range(233,239), // harvestable - tree[Inspect,Guide,Chop-down,Pick-{fruit}]
        IntStream.range(240,245), // diseased    - tree[Inspect,Guide,Prune]
        IntStream.range(246,251), // dead        - tree[Inspect,Guide,Clear]
        IntStream.range(252,252), // remove      - tree[Inspect,Guide,Clear] (stump)
        IntStream.range(253,253)  // grown       - tree[Inspect,Guide,Check-health]
    );

    public final IntStream weeds = IntStream.range(0, 2);

    public final IntStream plant = IntStream.range(3, 3);

    public final IntStream growing;

    public final IntStream harvestable;

    public final IntStream diseased;

    public final IntStream dead;

    public final IntStream remove;

    public final IntStream grown;

    Crop(
        IntStream growing,
        IntStream harvestable,
        IntStream diseased,
        IntStream dead,
        IntStream remove,
        IntStream grown
    ) {
        this.growing = growing;
        this.harvestable = harvestable;
        this.diseased = diseased;
        this.dead = dead;
        this.remove = remove;
        this.grown = grown;
    }
}
