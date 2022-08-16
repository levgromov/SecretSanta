import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SecretSanta{
    boolean listEquality = true;
    String namesList = "";
    String chName = "";
    String yourCoupleS;
    String vowels = "а,я";
    String consonants = "б,в,г,д,ж,з,к,л,м,н,п,р,с,т,ф,х,ц,ч,ш,щ";
    int i;

    ArrayList<String> members = new ArrayList<>();
    ArrayList<String> randomMembers = new ArrayList<>();

    JFrame frame;
    JPanel hiP;
    JPanel memberNameAreaP;
    JPanel listCompletedP;
    JLabel hi;
    JLabel inMembersL;
    JLabel nameArea;
    JTextField memberNameTF;
    JButton addB;
    JButton deleteB;
    JButton deleteListB;
    JLabel addedOrNotL;
    JButton okB;
    JButton noB;
    JButton yesB;
    JButton listCompletedB;

    JPanel inst;
    JPanel wayToCouple;
    JLabel reInMembersL;
    JButton returnB;
    JPanel coupleP;
    JTextField tapYourNameTF;
    JLabel coupleL;

    public void buildGui() {
        frame = new JFrame("Тайный Санта");

        hiP = new JPanel();
        memberNameAreaP = new JPanel();
        listCompletedP = new JPanel();

        hiP.setLayout(new BoxLayout(hiP, BoxLayout.Y_AXIS));

        hi = new JLabel("<html> Привет! Это игра \"Тайный Санта\". <br> Создайте список участников. Для этого введите имя участника в поле \"Имя участника\" <br> и нажмите кнопку \"Добавить\". Участники будут появляться в списке \"В игре учавствуют\". <br> По окончании пополнения списка нажмите кнопку \"Список закончен\". </html>");
        inMembersL = new JLabel("В игре учавствуют:");
        addedOrNotL = new JLabel("<html><br><br><br><br><br></html>");
        okB = new JButton("Понятно");
        okB = new JButton("Понятно");
        okB.addActionListener(new OkBListener());
        nameArea = new JLabel("Имя участника: ");
        memberNameTF = new JTextField(10);
        addB = new JButton("Добавить");
        addB.addActionListener(new AddBListener());
        deleteB = new JButton("Удалить");
        deleteB.addActionListener(new DeleteBListener());
        deleteListB = new JButton("Очистить список");
        deleteListB.addActionListener(new DeleteListBListener());
        noB = new JButton("Нет");
        noB.addActionListener(new NoBListener());
        yesB = new JButton("Да");
        yesB.addActionListener(new YesBListener());
        listCompletedB = new JButton("Список закончен");
        listCompletedB.addActionListener(new EndListListener());

        hiP.add(hi);
        hiP.add(inMembersL);
        hiP.add(addedOrNotL);
        memberNameAreaP.add(nameArea);
        memberNameAreaP.add(memberNameTF);
        memberNameAreaP.add(addB);
        memberNameAreaP.add(deleteB);
        memberNameAreaP.add(deleteListB);
        listCompletedP.add(listCompletedB);

        frame.getContentPane().add(BorderLayout.NORTH, hiP);
        frame.getContentPane().add(BorderLayout.CENTER, memberNameAreaP);
        frame.getContentPane().add(BorderLayout.SOUTH, listCompletedP);

        frame.setBounds(650, 300, 600, 290);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addName() {
        if (!memberNameTF.getText().equals("")) {
            if (!members.contains(memberNameTF.getText())) {
                members.add(memberNameTF.getText());
                namesList = members.get(0);

                for (i = 1; i < members.size(); i++) {
                    namesList += (", " + members.get(i));
                }

                inMembersL.setText("<html> В игре учавствуют: " + namesList + "</html>");
                memberNameTF.setText("");
            } else {
                addedOrNotL.setText("<html> <br><br><br> Человек с именем "  + memberNameTF.getText() + " уже добавлен в список участников.</html>");
                hiP.add(okB);
            }
        }
    }

    public void deleteName() {
        if (!memberNameTF.getText().equals("")) {
            if (members.contains(memberNameTF.getText())) {
                members.remove(memberNameTF.getText());

                if (members.size() > 0) {
                    namesList = members.get(0);
                }

                for (i = 1; i < members.size(); i++) {
                    namesList += (", " + members.get(i));
                }

                if (members.isEmpty()) {
                    namesList = "";
                }

                inMembersL.setText("<html> В игре учавствуют: " + namesList + "</html>");

                memberNameTF.setText("");
            } else {
                addedOrNotL.setText("<html> <br><br><br> Человек с именем " + memberNameTF.getText() + " ещё не добавлен в список участников </html>");
                hiP.add(okB);
            }
        }
    }

    public void ok() {
        addedOrNotL.setText("<html><br><br><br><br><br></html>");
        hiP.remove(okB);
        memberNameTF.setText("");
    }

    public void warning() {
        if (!members.isEmpty()) {
            addedOrNotL.setText("<html> <br> Уверены, что хотите очистить весь список участников? </html>");
            hiP.add(noB);
            hiP.add(yesB);
        }
    }

    public void no() {
        addedOrNotL.setText("<html><br><br><br><br><br></html>");
        hiP.remove(noB);
        hiP.remove(yesB);
    }

    public void yes() {
        members.clear();
        namesList = "";
        inMembersL.setText("В игре учавствуют:");
        addedOrNotL.setText("<html><br><br><br><br><br></html>");
        hiP.remove(noB);
        hiP.remove(yesB);
    }

    public void rebuildGui() {
        frame.remove(hiP);
        frame.remove(memberNameAreaP);
        frame.remove(listCompletedP);
        frame.repaint();

        inst = new JPanel();
        wayToCouple = new JPanel();
        coupleP = new JPanel();

        inst.setLayout(new BoxLayout(inst, BoxLayout.Y_AXIS));

        JLabel instP = new JLabel("<html> Участники были хорошенько перемешаны и разбиты по парам. <br> Чтобы узнать свою пару, введите своё имя в поле \"Ваше имя\" и нажмите кнопку \"Узнать пару\". <br> Запомните кому дарите подарок и ОБЯЗАТЕЛЬНО нажмите кнопку \"Следующий участник\", <br> чтобы никто не узнал с кем вы в паре. </html>");
        reInMembersL = new JLabel("<html> В игре учавствуют: " + namesList + "</html>");
        returnB = new JButton("Веруться к созданию списка");
        returnB.addActionListener(new ReturnBListener());
        JLabel yourName = new JLabel("Ваше имя: ");
        tapYourNameTF = new JTextField(10);
        JButton knowCouple = new JButton("Узнать пару");
        knowCouple.addActionListener(new ShowCoupleListener());
        JButton nextMember = new JButton("Следующий участник");
        nextMember.addActionListener(new NextMemberListener());
        coupleL = new JLabel();

        inst.add(instP);
        inst.add(reInMembersL);
        inst.add(returnB);
        wayToCouple.add(yourName);
        wayToCouple.add(tapYourNameTF);
        wayToCouple.add(knowCouple);
        wayToCouple.add(nextMember);
        coupleP.add(coupleL);

        frame.getContentPane().add(BorderLayout.NORTH, inst);
        frame.getContentPane().add(BorderLayout.CENTER, wayToCouple);
        frame.getContentPane().add(BorderLayout.SOUTH, coupleP);
        frame.repaint();
        frame.setVisible(true);

        if (!members.isEmpty() & members.size() == randomMembers.size()) {
            int numEquals = 0;
            listEquality = false;
            for (String nameM : members) {
                for (String nameRM : randomMembers) {
                    if (nameM.equals(nameRM)) {
                        numEquals++;
                        break;
                    }
                }
                if (numEquals == members.size()) {
                    listEquality = true;
                }
            }
        }

        if ((!members.isEmpty() & members.size() != randomMembers.size()) || (!listEquality & members.size() == randomMembers.size())) {
            randomMembers.clear();
            for (i = 0; i < members.size(); i++) {
                int rand = members.size();
                while (rand > members.size() - 1) {
                    rand = (int) (Math.random() * 100);
                }
                if (!randomMembers.contains(members.get(rand))) {
                    randomMembers.add(members.get(rand));
                } else {
                    i--;
                }
            }
            for (i = 0; i < randomMembers.size() - 1; i++) {
                System.out.println(randomMembers.get(i) + " дарит подарок человеку с именем " + randomMembers.get(i + 1));
            }
            System.out.println(randomMembers.get(randomMembers.size() - 1) + " дарит подарок человеку с именем " + randomMembers.get(0));
        }
    }

    public void returnV() {
        frame.remove(inst);
        frame.remove(wayToCouple);
        frame.remove(coupleP);
        frame.repaint();

        frame.getContentPane().add(BorderLayout.NORTH, hiP);
        frame.getContentPane().add(BorderLayout.CENTER, memberNameAreaP);
        frame.getContentPane().add(BorderLayout.SOUTH, listCompletedP);

        frame.setVisible(true);
    }

    public String changeName(String name) {
        chName = name;
        if (vowels.contains(chName.substring(chName.length() - 1))) chName = chName.substring(0, chName.length() - 1) + "е";
        if (consonants.contains(chName.substring(chName.length() - 1))) chName += "у";
        if (chName.charAt(chName.length() - 1) == 'ь' || chName.charAt(chName.length() - 1) == 'й') chName = chName.substring(0, chName.length() - 1) + "ю";
        return chName;
    }

    public void showCouple() {
        if (!tapYourNameTF.getText().equals("")) {
            if (randomMembers.contains(tapYourNameTF.getText())) {
                if (randomMembers.indexOf(tapYourNameTF.getText()) == randomMembers.size() - 1) {
                    yourCoupleS = changeName(randomMembers.get(0));
                } else {
                    yourCoupleS = changeName(randomMembers.get(randomMembers.indexOf(tapYourNameTF.getText()) + 1));
                }
                coupleL.setText("<html>" + tapYourNameTF.getText() + ", вы дарите подарок " + yourCoupleS + " <br><br> Не забудьте нажать кноку \"Следующий участник\" <br><br> </html>");
            } else {
                coupleL.setText("<html> Человека с таким именем нет среди участников <br><br><br><br> </html>");
            }
        } else {
            coupleL.setText("<html> Сначала введите имя <br><br><br><br> </html>");
        }
        yourCoupleS = "";
    }

    public void nextCouple() {
        tapYourNameTF.setText("");
        coupleL.setText("");
    }

    class AddBListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            addName();
        }
    }

    class DeleteBListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            deleteName();
        }
    }

    class OkBListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            ok();
        }
    }

    class DeleteListBListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            warning();
        }
    }

    class NoBListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            no();
        }
    }

    class YesBListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            yes();
        }
    }

    class EndListListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            rebuildGui();
        }
    }

    class ReturnBListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            returnV();
        }
    }

    class ShowCoupleListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            showCouple();
        }
    }

    class NextMemberListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            nextCouple();
        }
    }
}