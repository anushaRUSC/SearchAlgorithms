import java.io.*;
import java.util.*;

/**
 * Created by Anusha on 9/8/2016.
 */
public class homework {

    public static void main(String args[]) {

        for (int p = 0; p < 165; p++) {
            //String fileName = "input.txt";
            //String fileName = "C:\\Users\\Anusha\\Desktop\\Internship\\Java\\AI_HW1\\src\\input.txt";


            //Map<String,Map<String,Integer>> map = new HashMap<String, Map<String, Integer>>();
            //String line = null;
            algorithm(p);
        }
    }

    public static void algorithm(int w) {

        String fileName = "C:\\Users\\Anusha\\Desktop\\USC\\Sem3\\AI\\Assignments\\HW1\\CSCI561-master\\CSCI561-master\\cases\\input" + w + ".txt";
        String opFile = "C:\\Users\\Anusha\\Desktop\\out2\\output" + w + ".txt";
            try {

                Map<String, Map<String, Integer>> map = new HashMap<String, Map<String, Integer>>();
                FileReader fileReader = new FileReader(fileName);

                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String algo = bufferedReader.readLine();
                String source = bufferedReader.readLine();
                String goal = bufferedReader.readLine();
                ///System.out.println("Source is:" + source + " and Goal is:" + goal);
                Integer num = Integer.parseInt(bufferedReader.readLine());

                Map<String, Integer> exist_item = new LinkedHashMap<String, Integer>();

                for (int i = 0; i < num; i++) {
                    String str = bufferedReader.readLine();
                    String[] split = str.split(" ");

                    ///System.out.println("Printitng line" + i);
                    ///System.out.println(Arrays.toString(split));

                    Map<String, Integer> elem = map.get(split[0]);
                    if (elem == null) {
                        Map<String, Integer> item = new LinkedHashMap<String, Integer>();
                        item.put(split[1], Integer.valueOf(split[2]));
                        map.put(split[0], item);
                    } else {
                        exist_item = map.get(split[0]);
                        exist_item.put(split[1], Integer.valueOf(split[2]));
                    }
                }

                int SundayLineNum = Integer.parseInt(bufferedReader.readLine());
                Map<String, Integer> SundayTraffic = new HashMap<String, Integer>();

                for (int i = 0; i < SundayLineNum; i++) {
                    String SundayLine = bufferedReader.readLine();
                    String[] SundaySplit = SundayLine.split(" ");
                    SundayTraffic.put(SundaySplit[0], Integer.valueOf(SundaySplit[1]));
                }

                ///System.out.println("Sunday Traffic: " + SundayTraffic);

                if (algo.equals("BFS")) {
                    ///System.out.println("BFS");

                    if (source.equals(goal)) {
                        FileWriter writer = new FileWriter(opFile);
                        writer.write(source + " 0");
                        //writer.write("\n");
                        writer.close();
                        return;
                    }

                    HashSet<String> flag = new HashSet<String>();
                    Map<String, String> prev = new HashMap<String, String>();
                    Map<String, Integer> dist = new HashMap<String, Integer>();

                    Queue<String> q = new LinkedList<String>();
                    flag.add(source);
                    prev.put(source, "Start");
                    dist.put(source, 0);
                    q.add(source);
                    while (!q.isEmpty()) {
                        //System.out.println("Queue: " + q);
                        String popped = q.remove();


                        if (popped.equals(goal)) {
                            String n = new String(goal);
                            ArrayList<String> result = new ArrayList<String>();
                            while (!prev.get(n).equals("Start")) {
                                String x = new String(prev.get(n));
                                result.add(n + " " + dist.get(n));
                                //System.out.println( n + " " + dist.get(n));
                                n = new String(x);
                            }
                            result.add(source + " " + "0");
                            Collections.reverse(result);
                            ///System.out.println(result);

                            FileWriter writer = new FileWriter(opFile);
                            for (String str : result) {
                                writer.write(str + System.getProperty("line.separator"));
                            }
                            writer.close();

                            return;
                        }

                        Map<String, Integer> node = map.get(popped);

                        if (node == null)
                            continue;

                        Iterator it = node.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry) it.next();

                            if (!flag.contains(pair.getKey().toString())) {
                                flag.add(pair.getKey().toString());
                                prev.put(pair.getKey().toString(), popped);
                                dist.put(pair.getKey().toString(), dist.get(popped) + 1);
                                q.add(pair.getKey().toString());
                            }

                        }
                        //it.remove(); // avoids a ConcurrentModificationException
                    }

                    // Print path cost
                    String n = goal;
                    String x = prev.get(n);
                    while (!prev.get(x).equals(source)) {
                        n = prev.get(x);
                        ///System.out.println(x + " " + n + dist.get(n));
                    }
                }

                if (algo.equals("UCS")) {
                    ///System.out.println(algo);
                    //System.out.println(map);

                    Map<String, Integer> open = new LinkedHashMap<String, Integer>();
                    open.put(source, 0);

                    // System.out.print(open);
                    Map<String, Integer> closed = new HashMap<String, Integer>();
                    Map<String, String> prev = new HashMap<String, String>();
                    prev.put(source, "Start");

                    if (open.isEmpty())
                        return;

                    while (!open.isEmpty()) {
                        String curr = new String(open.entrySet().iterator().next().getKey());
                        Integer cost = new Integer(open.entrySet().iterator().next().getValue());

                        ///System.out.println("Current node is " + curr);
                        //open.remove(curr);
                        if (curr.equals(goal)) {
                            open.remove(curr);
                            closed.put(curr, cost);
                            ///System.out.print("Closed:" + closed);
                            ///System.out.print("Open:" + open);
                            ///System.out.print("Map:" + map);
                            ///System.out.println("");
                            ///System.out.println("Prev:" + prev);

                            String n = new String(goal);
                            ArrayList<String> result = new ArrayList<String>();
                            while (!prev.get(n).equals("Start")) {
                                String x = new String(prev.get(n));
                                result.add(n + " " + closed.get(n));
                                //System.out.println( n + " " + dist.get(n));
                                n = new String(x);
                            }

                            result.add(source + " " + "0");
                            Collections.reverse(result);
                            ///System.out.println(result);

                            FileWriter writer = new FileWriter(opFile);
                            for (String str : result) {
                                writer.write(str + System.getProperty("line.separator"));
                                //writer.write("\n");
                            }
                            writer.close();
                            return;
                        } else {
                            //System.out.println(open);
                            open.remove(curr);
                            Map<String, Integer> children = map.get(curr);

                            if (children == null)
                                continue;

                            //System.out.println(children);
                            Iterator it = children.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry child = (Map.Entry) it.next();
                                children.remove(child);
                                //prev.put(child.getKey().toString(),curr);

                                if ((!open.containsKey(child.getKey().toString())) && (!closed.containsKey(child.getKey().toString()))) {
                                    open.put(child.getKey().toString(), Integer.parseInt(child.getValue().toString()) + cost);
                                    prev.put(child.getKey().toString(), curr);
                                    ///System.out.println("Prev is: " + prev);
                                } else if (open.containsKey(child.getKey())) {
                                    Integer ChildCost = Integer.parseInt(child.getValue().toString()) + cost;
                                    ///System.out.println( " Child is:"+ child.getKey().toString() +" | Child cost:" + ChildCost);
                                    Integer NodeCost = Integer.parseInt(open.get(child.getKey()).toString());

                                    //int NewChildCost = ChildCost.intValue() + cost
                                    //ChildCost=cost;
                                    //Integer temp = new Integer(ChildCost.intValue() + cost.intValue());
                                    //ChildCost=temp;
                                    if (ChildCost.intValue() < NodeCost.intValue()) {
                                        open.remove(child.getKey());
                                        //open.put(child.getKey().toString(),Integer.parseInt(child.getValue().toString()));
                                        open.put(child.getKey().toString(), ChildCost);
                                        prev.remove(child.getKey().toString());
                                        prev.put(child.getKey().toString(), curr);
                                        ///System.out.println("Prev is: " + prev);
                                    }
                                } else if (closed.containsKey(child.getKey())) {
                                    Integer ChildCost = Integer.parseInt(child.getValue().toString()) + cost;
                                    Integer NodeCost = Integer.parseInt(closed.get(child.getKey()).toString());

                                    //ChildCost+=cost;
                                    if (ChildCost.intValue() < NodeCost.intValue()) {
                                        closed.remove(child.getKey());
                                        open.put(child.getKey().toString(), ChildCost);
                                        prev.remove(child.getKey().toString());
                                        prev.put(child.getKey().toString(), curr);
                                        ///System.out.println("Prev is: " + prev);
                                    }
                                }
                            }

                        }
                        closed.put(curr, cost);

                        ///System.out.println("Before sorting open:" + open);
                        // sort open
                        Set<Map.Entry<String, Integer>> set = open.entrySet();
                        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);
                        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                                return (o1.getValue()).compareTo(o2.getValue());
                            }
                        });
                        open.clear();
                        for (Map.Entry<String, Integer> entry : list) {
                            open.put(entry.getKey(), entry.getValue());
                        }
                        ///System.out.println("After sorting open:" + open);
                    }
                }

                if (algo.equals("DFS")) {
                    System.out.println("DFS");
                    Stack<String> stk = new Stack<String>();
                    stk.push(source);
                    Set<String> discovered = new HashSet<String>();
                    Map<String, Integer> dist = new HashMap<String, Integer>();
                    dist.put(source, 0);
                    Map<String, String> prev = new HashMap<String, String>();
                    prev.put(source, "Start");
                    //discovered.add(source);

                    while (!stk.isEmpty()) {
                        ///System.out.println("Stack contents before popping: " + stk);
                        String popped = stk.pop();
                        //System.out.println("popped node: " + popped);

                        if (popped.equals(goal)) {
                            ///System.out.println("goal found");
                            ///System.out.println("Path cost: " + dist.get(goal));
                            ///System.out.println("Prev: " + prev);

                            String n = new String(goal);

                            ArrayList<String> result = new ArrayList<String>();
                            while (!prev.get(n).equals("Start")) {
                                String x = new String(prev.get(n));
                                result.add(n + " " + dist.get(n));
                                //System.out.println( n + " " + dist.get(n));
                                n = new String(x);
                            }

                            result.add(source + " " + "0");
                            Collections.reverse(result);
                            //System.out.println(result);

                            FileWriter writer = new FileWriter(opFile);
                            for (String str : result) {
                                writer.write(str + System.getProperty("line.separator"));
                                //writer.write("\n");
                            }
                            writer.close();

                            return;
                        }


                        if (!map.containsKey(popped))
                            continue;

                        Stack<String> tempStk = new Stack<String>();
                        if (!discovered.contains(popped)) {
                            discovered.add(popped);

                            Map<String, Integer> children = map.get(popped);

                            ///System.out.println(children);
                            Iterator it = children.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry child = (Map.Entry) it.next();

                                //if(stk.contains(child.getKey().toString()))
                                //    continue;

                                if (!discovered.contains(child.getKey().toString()) && !stk.contains(child.getKey().toString())) {
                                    tempStk.push(child.getKey().toString());
                                    dist.put(child.getKey().toString(), dist.get(popped) + 1);
                                    prev.put(child.getKey().toString(), popped);
                                    //discovered.add(child.getKey().toString());
                                }
                            }
                        }

                        while (!tempStk.isEmpty()) {
                            stk.push(tempStk.pop());
                        }

                        //}
                    }
                }

                if (algo.equals("A*")) {
                    ///System.out.println("A*");

                    Map<String, Integer> open = new LinkedHashMap<String, Integer>();
                    open.put(source, SundayTraffic.get(source));

                    Map<String, Integer> closed = new HashMap<String, Integer>();
                    Map<String, String> prev = new HashMap<String, String>();
                    Map<String, Integer> ActualPathCost = new HashMap<String, Integer>();
                    ActualPathCost.put(source, 0);
                    prev.put(source, "Start");
                    //EstimateCost.put(source,0);

                    while (!open.isEmpty()) {
                        String curr = new String(open.entrySet().iterator().next().getKey());
                        Integer cost = new Integer(open.entrySet().iterator().next().getValue());

                        ///System.out.println("Current node is " + curr);
                        //open.remove(curr);
                        if (curr.equals(goal)) {

                            ///System.out.println("Path cost: " + ActualPathCost.get(goal));
                            ///System.out.println("Prev: " + prev);

                            String n = new String(goal);

                            ArrayList<String> result = new ArrayList<String>();
                            while (!prev.get(n).equals("Start")) {
                                String x = new String(prev.get(n));
                                result.add(n + " " + ActualPathCost.get(n));
                                //System.out.println( n + " " + dist.get(n));
                                n = new String(x);
                            }

                            result.add(source + " " + "0");
                            Collections.reverse(result);
                            ///System.out.println(result);

                            FileWriter writer = new FileWriter(opFile);
                            for (String str : result) {
                                writer.write(str + System.getProperty("line.separator"));
                                //writer.write("\n");
                            }
                            writer.close();

                            return;

                        } else {
                            open.remove(curr);
                            ///System.out.println("ActualPath Cost to Curr: " +  ActualPathCost.get(curr));
                            Map<String, Integer> children = map.get(curr);

                            if (children == null) continue;
                            //System.out.println(children);
                            Iterator it = children.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry child = (Map.Entry) it.next();
                                children.remove(child);
                                //prev.put(child.getKey().toString(),curr);
                                ///System.out.println("Child of " + curr +" node is: " + child);

                                if ((!open.containsKey(child.getKey().toString())) && (!closed.containsKey(child.getKey().toString()))) {
                                    ///System.out.println( child + " is neither present in open nor closed list");
                                    //System.out.println("ActualPath Cost to Curr: " +  ActualPathCost.get(curr));
                                    int ActualCostToNode = Integer.parseInt(child.getValue().toString()) + ActualPathCost.get(curr);
                                    open.put(child.getKey().toString(), ActualCostToNode + SundayTraffic.get(child.getKey()));
                                    prev.put(child.getKey().toString(), curr);
                                    //System.out.println("Prev is: " + prev);
                                    ActualPathCost.put(child.getKey().toString(), ActualPathCost.get(curr) + Integer.parseInt(child.getValue().toString()));
                                    //ActualCost.put(child.getKey().toString(), cost + Integer.parseInt(child.getValue().toString()) + SundayTraffic.get(child.getKey().toString()));
                                    ///System.out.println("Open after inserting child is: " + open);
                                    ///System.out.println("updated Prev is: " + prev);
                                    ///System.out.println("ActualPath Cost to :" + child + " is " + ActualPathCost.get(curr));
                                } else if (open.containsKey(child.getKey())) {
                                    ///System.out.println(child + " is present in open");
                                    Integer ChildCost = ActualPathCost.get(curr) + Integer.parseInt(child.getValue().toString())
                                            + SundayTraffic.get(child.getKey());
                                    //System.out.println("Cost to child is " + ChildCost);
                                    //System.out.println("h value of child: " + SundayTraffic.get(child.getKey()));

                                    //System.out.println( " Child is:"+ child.getKey().toString() +" | Child cost:" + ChildCost);
                                    Integer NodeCost = Integer.parseInt(open.get(child.getKey()).toString());

                                    if (ChildCost.intValue() < NodeCost.intValue()) {
                                        open.remove(child.getKey());
                                        //open.put(child.getKey().toString(),Integer.parseInt(child.getValue().toString()));
                                        open.put(child.getKey().toString(), ChildCost);
                                        prev.remove(child.getKey().toString());
                                        prev.put(child.getKey().toString(), curr);
                                        ActualPathCost.remove(child.getKey().toString());
                                        ActualPathCost.put(child.getKey().toString(), ActualPathCost.get(curr) + Integer.parseInt(child.getValue().toString()));
                                        ///System.out.println("Prev is: " + prev);
                                    }
                                } else if (closed.containsKey(child.getKey())) {
                                    Integer ChildCost = ActualPathCost.get(curr) + Integer.parseInt(child.getValue().toString()) + SundayTraffic.get(child.getKey());
                                    Integer NodeCost = Integer.parseInt(closed.get(child.getKey()).toString());

                                    //ChildCost+=cost;
                                    if (ChildCost.intValue() < NodeCost.intValue()) {
                                        closed.remove(child.getKey());
                                        //open.put(child.getKey().toString(),ChildCost);
                                        open.put(child.getKey().toString(), ChildCost);
                                        prev.remove(child.getKey().toString());
                                        prev.put(child.getKey().toString(), curr);
                                        ///System.out.println("Prev is: " + prev);
                                        ///System.out.println("Closed is :" + closed);
                                        ///System.out.println("Open is: " + open);

                                        ActualPathCost.remove(child.getKey());
                                        ActualPathCost.put(child.getKey().toString(), ActualPathCost.get(curr) +
                                                Integer.parseInt(child.getValue().toString()));

                                        ///System.out.println("Open now is :" + open);
                                        ///System.out.println("Prev now is :" + prev);
                                    }
                                }
                            }

                        }
                        closed.put(curr, cost);

                        ///System.out.println("Before sorting open:" + open);
                        // sort open
                        Set<Map.Entry<String, Integer>> set = open.entrySet();
                        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);
                        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                                return (o1.getValue()).compareTo(o2.getValue());
                            }
                        });
                        open.clear();
                        for (Map.Entry<String, Integer> entry : list) {
                            open.put(entry.getKey(), entry.getValue());
                        }
                        ///System.out.println("After sorting open:" + open);
                    }


                }

                // Always close files.
                bufferedReader.close();
            } catch (FileNotFoundException ex) {
                System.out.println(
                        "Unable to open file '" +
                                fileName + "'");
            } catch (IOException ex) {
                System.out.println(
                        "Error reading file '"
                                + fileName + "'");
                // Or we could just do this:
                // ex.printStackTrace();
            }

        }
    }

