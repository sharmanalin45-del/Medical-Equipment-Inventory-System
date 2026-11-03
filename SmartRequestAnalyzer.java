
package pages;

public class SmartRequestAnalyzer {

    // ==========================================
    // 1. CALCULATE SMART PRIORITY SCORE
    // ==========================================

    public static int calculatePriority(
            String department,
            String purpose,
            String equipmentName,
            int waitingDays
    ) {

        int score = 0;

        // ==========================================
        // DEPARTMENT PRIORITY
        // ==========================================

        if (department != null) {

            String dept = department.toLowerCase();

            if (dept.contains("emergency")
                    || dept.contains("er")
                    || dept.contains("icu")) {

                score += 35;

            } else if (dept.contains("surgery")
                    || dept.contains("operation")
                    || dept.contains("ot")) {

                score += 30;

            } else if (dept.contains("cardiology")
                    || dept.contains("nicu")
                    || dept.contains("pediatric")) {

                score += 28;

            } else if (dept.contains("lab")
                    || dept.contains("radiology")) {

                score += 22;

            } else {

                score += 15;
            }
        }

        // ==========================================
        // URGENCY / PURPOSE PRIORITY
        // ==========================================

        if (purpose != null) {

            String urgency = purpose.toLowerCase();

            if (urgency.contains("emergency")
                    || urgency.contains("critical")
                    || urgency.contains("life saving")
                    || urgency.contains("immediate")
                    || urgency.contains("urgent")) {

                score += 35;

            } else if (urgency.contains("operation")
                    || urgency.contains("surgery")
                    || urgency.contains("patient")) {

                score += 25;

            } else if (urgency.contains("important")
                    || urgency.contains("priority")
                    || urgency.contains("soon")) {

                score += 18;

            } else {

                score += 8;
            }
        }

        // ==========================================
        // EQUIPMENT CRITICALITY
        // ==========================================

        if (equipmentName != null) {

            String equipment =
                    equipmentName.toLowerCase();

            if (equipment.contains("ventilator")
                    || equipment.contains("defibrillator")
                    || equipment.contains("dialysis")
                    || equipment.contains("anesthesia")
                    || equipment.contains("oxygen")
                    || equipment.contains("life support")) {

                score += 20;

            } else if (equipment.contains("ecg")
                    || equipment.contains("monitor")
                    || equipment.contains("infusion")
                    || equipment.contains("ultrasound")
                    || equipment.contains("x-ray")) {

                score += 15;

            } else {

                score += 8;
            }
        }

        // ==========================================
        // WAITING TIME PRIORITY
        // 2 POINTS PER DAY, MAXIMUM 15
        // ==========================================

        int waitingScore =
                Math.min(waitingDays * 2, 15);

        if (waitingScore > 0) {
            score += waitingScore;
        }

        // ==========================================
        // MAXIMUM SCORE = 100
        // ==========================================

        return Math.min(score, 100);
    }


    // ==========================================
    // 2. DETERMINE PRIORITY LEVEL
    // ==========================================

    public static String getPriorityLevel(int score) {

        if (score >= 75) {

            return "HIGH";

        } else if (score >= 50) {

            return "MEDIUM";

        } else {

            return "LOW";
        }
    }


    // ==========================================
    // 3. DETECT EQUIPMENT REQUEST CONFLICT
    // ==========================================

    public static boolean hasConflict(
            int availableQuantity,
            int pendingQuantity
    ) {

        return pendingQuantity > availableQuantity;
    }


    // ==========================================
    // 4. GENERATE SMART RECOMMENDATION
    // ==========================================

    public static String getRecommendation(
            int score,
            boolean conflict
    ) {

        String priority =
                getPriorityLevel(score);

        // ==========================================
        // HIGH PRIORITY + CONFLICT
        // ==========================================

        if (priority.equals("HIGH") && conflict) {

            return "URGENT: High priority request with " +
                    "equipment shortage. Arrange immediate " +
                    "procurement or transfer.";
        }

        // ==========================================
        // HIGH PRIORITY WITHOUT CONFLICT
        // ==========================================

        if (priority.equals("HIGH") && !conflict) {

            return "HIGH PRIORITY: Approve urgently " +
                    "and allocate equipment immediately.";
        }

        // ==========================================
        // MEDIUM PRIORITY + CONFLICT
        // ==========================================

        if (priority.equals("MEDIUM") && conflict) {

            return "MEDIUM PRIORITY: Equipment shortage " +
                    "detected. Check alternative equipment " +
                    "or arrange procurement.";
        }

        // ==========================================
        // MEDIUM PRIORITY WITHOUT CONFLICT
        // ==========================================

        if (priority.equals("MEDIUM") && !conflict) {

            return "MEDIUM PRIORITY: Review and process " +
                    "after high priority requests.";
        }

        // ==========================================
        // LOW PRIORITY + CONFLICT
        // ==========================================

        if (priority.equals("LOW") && conflict) {

            return "LOW PRIORITY: Stock shortage detected. " +
                    "Process as per availability.";
        }

        // ==========================================
        // LOW PRIORITY WITHOUT CONFLICT
        // ==========================================

        return "LOW PRIORITY: Process as per normal workflow.";
    }
}
