package fr.uparis.energy.model;

public class SourceComponent extends Component {
    @Override
    public boolean isPowered() {
        return true;
    }

    @Override
    public String toString() {
        return "S";
    }
}
