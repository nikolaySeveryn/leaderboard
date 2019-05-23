package nick.leaderboard.listener;


public class PrintPositionChangeListener implements PositionChangeListener {

    @Override
    public void notify(PositionChangeEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("To: " + event.getReceiverId());
        stringBuilder.append(" Total members: " + event.getTotalCount());
        stringBuilder.append(" Leaderboard {");
        for (PositionChangeEvent.PositionItem positionItem : event.getItems()) {
            stringBuilder.append(positionItem.getPosition());
            stringBuilder.append(".id=");
            stringBuilder.append(positionItem.getUserId());
            stringBuilder.append(".points=");
            stringBuilder.append(positionItem.getPoints());
            stringBuilder.append(",");
        }
        stringBuilder.append("}");
        System.out.println(stringBuilder.toString());
    }
}
